package arch.example.trader.layer.service

import arch.example.trader.layer.domain.Order
import arch.example.trader.layer.domain.OrderType
import arch.example.trader.layer.dto.OrderDto
import arch.example.trader.layer.repository.DealRepository
import arch.example.trader.layer.repository.IPORepository
import arch.example.trader.layer.repository.OrderRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalTime
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class OrderService(
    val orderMatchingService: OrderMatchingService,
    val dealRepository: DealRepository,
    val ipoRepository: IPORepository,
    val walletService: WalletService,
    val orderRepository: OrderRepository,
    val portfolioService: PortfolioService
) {

    fun findOrder(orderId: UUID): Order? {
        return orderRepository.findById(orderId).getOrNull()
    }


    fun placeOrder(orderDto: OrderDto) {
        val marketPrice = getMarketPrice(orderDto.assetId)

        val wallet = walletService.findUserWallet(orderDto.traderId)
            ?: throw RuntimeException("User has no Wallet ${orderDto.traderId}")

        if (
            priceIsValid(orderDto, marketPrice) &&
            quantityIsValid(orderDto) &&
            marketIsOpen(orderDto) && (orderDto.type != OrderType.BUY || userHasSufficientFunds(
                orderDto,
                wallet.balance
            ))
            && (orderDto.type != OrderType.SELL || userHasEnoughAssetsToSell(
                orderDto.traderId,
                orderDto.assetId,
                orderDto.quantity
            ))
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
            dto.unitPrice,
            dto.type,
            dto.quantity,
            dto.placedAt
        )

    private fun priceIsValid(orderDto: OrderDto, marketPrice: BigDecimal): Boolean {
        val priceLimit = 0.10
        val minPrice = marketPrice.multiply(BigDecimal(1 - priceLimit))
        val maxPrice = marketPrice.multiply(BigDecimal(1 + priceLimit))

        return orderDto.unitPrice in minPrice..maxPrice
    }

    private fun getMarketPrice(tradableId: UUID): BigDecimal {
        return dealRepository.findByTradableIdOrderByCreatedAtDesc(tradableId)?.unitPrice
            ?: ipoRepository.findOneByTradableId(tradableId)?.price
            ?: throw IllegalStateException("No price for the asset: $tradableId")
    }

    fun userHasSufficientFunds(orderDto: OrderDto, userBalance: BigDecimal): Boolean {
        return BigDecimal(orderDto.quantity).multiply(orderDto.unitPrice) <= userBalance
    }

    private fun userHasEnoughAssetsToSell(traderId: UUID, assetId: UUID, quantity: Long): Boolean {
        return portfolioService.getAssetQuantity(
            traderId,
            assetId
        ) >= quantity
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