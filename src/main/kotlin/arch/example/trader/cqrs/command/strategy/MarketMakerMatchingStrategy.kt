package arch.example.trader.cqrs.command.strategy

import arch.example.trader.cqrs.command.domain.entity.Deal
import arch.example.trader.cqrs.command.domain.entity.DealId
import arch.example.trader.cqrs.command.domain.entity.Order
import arch.example.trader.cqrs.command.domain.repository.DealRepository
import arch.example.trader.cqrs.command.domain.repository.OrderBook
import java.time.Instant
import java.util.*

class MarketMakerMatchingStrategy(
    private val orderBook: OrderBook,
    private val dealRepository: DealRepository
) : OrderMatchingStrategy {

    override fun invoke(newOrder: Order): List<Deal> {
        val makerOrders = orderBook.getPassiveOrders()
        if (makerOrders.isNotEmpty()) {
            val deal = executeTrade(newOrder, makerOrders.first())

            applyTakerFee(newOrder)

            return listOf(deal)
        }

        orderBook.placeOrder(newOrder)
        return listOf()
    }

    private fun applyTakerFee(order: Order) {

    }

    private fun executeTrade(buyOrder: Order, sellOrder: Order): Deal {
        val matchedQuantity = minOf(buyOrder.quantity, sellOrder.quantity)
        val sealedDeal = Deal(
            DealId(UUID.randomUUID()), sellOrder.id, buyOrder.id, buyOrder.assetId,
            matchedQuantity, sellOrder.price, Instant.now()
        )


        orderBook.cancelOrder(buyOrder)
        orderBook.cancelOrder(sellOrder)

        dealRepository.save(sealedDeal)

        return sealedDeal
    }
}