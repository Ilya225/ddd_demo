package arch.example.trader.layer.service

import arch.example.trader.layer.dto.OrderDto
import arch.example.trader.layer.entity.Order
import arch.example.trader.layer.repository.DealRepository
import arch.example.trader.layer.repository.IPORepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalTime
import java.util.UUID

@Service
class OrderService(
    val orderMatchingService: OrderMatchingService,
    val dealRepository: DealRepository,
    val ipoRepository: IPORepository,
) {

    fun placeOrder(orderDto: OrderDto) {
        val marketPrice = getMarketPrice(orderDto.assetId)

        if (
            priceIsValid(orderDto, marketPrice) &&
            quantityIsValid(orderDto) &&
            marketIsOpen(orderDto)
        ) {
            val newOrder = createOrder(orderDto)
            orderMatchingService.matchOrder(newOrder)
        }
    }

    private fun createOrder(dto: OrderDto) =
        Order(
            UUID.randomUUID(),
            dto.traderId,
            dto.assetId,
            dto.price,
            dto.type,
            dto.quantity,
            dto.placedAt
        )

    fun priceIsValid(orderDto: OrderDto, marketPrice: BigDecimal): Boolean {
        val priceLimit = 0.10
        val minPrice = marketPrice.multiply(BigDecimal(1 - priceLimit))
        val maxPrice = marketPrice.multiply(BigDecimal(1 + priceLimit))

        return orderDto.price in minPrice..maxPrice
    }

    private fun getMarketPrice(tradableId: UUID): BigDecimal {
        return dealRepository.findByTradableIdOrderByCreatedAtDesc(tradableId)?.price
            ?: ipoRepository.findOneByTradableId(tradableId)?.price
            ?: throw IllegalStateException("No price for the asset: $tradableId")
    }

    fun userHasSufficientFunds(orderDto: OrderDto, userBalance: BigDecimal): Boolean {
        return BigDecimal(orderDto.quantity).multiply(orderDto.price) <= userBalance
    }

    fun isDecreasingOrder(orderDto: OrderDto, marketPrice: BigDecimal): Boolean {
        val dropPercentage = (marketPrice - orderDto.price) / marketPrice
        return dropPercentage >= BigDecimal(0.07)  // 7% limit
    }

    fun marketIsOpen(orderDto: OrderDto): Boolean {
        val marketOpen = LocalTime.of(9, 30)
        val marketClose = LocalTime.of(16, 0)

        return LocalTime.from(orderDto.placedAt) in marketOpen..marketClose
    }

    fun quantityIsValid(orderDto: OrderDto): Boolean {
        val minSize = 1
        val maxSize = 500

        return orderDto.quantity in minSize..maxSize
    }
}