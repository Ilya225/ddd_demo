package arch.example.trader.layer.service

import arch.example.trader.layer.domain.Deal
import arch.example.trader.layer.domain.Order
import arch.example.trader.layer.domain.OrderType
import arch.example.trader.layer.repository.DealRepository
import java.time.Instant
import java.util.UUID

class OrderMatchingService(
    private val orderBook: OrderBook,
    private val dealRepository: DealRepository
) {

    fun matchOrder(newOrder: Order) {
        val bestMatch = if (newOrder.type == OrderType.BUY) orderBook.getBestSellOrder(newOrder.assetId)
        else orderBook.getBestBuyOrder(newOrder.assetId)

        if (bestMatch != null && isMatch(newOrder, bestMatch)) {
            executeTrade(newOrder, bestMatch)
        } else {
            orderBook.addOrder(newOrder)
        }
    }

    private fun isMatch(order: Order, match: Order): Boolean {
        return if (order.type == OrderType.BUY) order.price >= match.price
        else order.price <= match.price
    }

    private fun executeTrade(buyOrder: Order, sellOrder: Order): Deal {
        val matchedQuantity = minOf(buyOrder.quantity, sellOrder.quantity)
        val sealedDeal = Deal(UUID.randomUUID(), sellOrder.id, buyOrder.id, buyOrder.assetId,
            matchedQuantity, sellOrder.price, Instant.now())

        // Adjust order book
        orderBook.removeOrder(buyOrder)
        orderBook.removeOrder(sellOrder)

        dealRepository.save(sealedDeal)

        return sealedDeal
    }
}
