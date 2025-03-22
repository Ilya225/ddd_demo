package arch.example.trader.cqrs.command.domain.policy.matching

import arch.example.trader.cqrs.command.domain.entity.Deal
import arch.example.trader.cqrs.command.domain.entity.DealId
import arch.example.trader.cqrs.command.domain.entity.MatcherType
import arch.example.trader.cqrs.command.domain.entity.Order
import arch.example.trader.cqrs.command.domain.entity.OrderType
import arch.example.trader.cqrs.command.domain.repository.DealRepository
import arch.example.trader.cqrs.command.domain.repository.OrderBook
import java.time.Instant
import java.util.*

class PriceTimeMatchingStrategy(
    private val orderBook: OrderBook,
    private val dealRepository: DealRepository
) : OrderMatchingStrategy {

    override fun invoke(newOrder: Order): List<Deal> {
        val bestMatches = orderBook.getMatchingOrders(newOrder)

        val deals = bestMatches.mapNotNull { bestMatch ->
            if (newOrder.isMatch(bestMatch)) {
                val (buyOrder, sellOrder) = prepareOrdersForDeal(newOrder, bestMatch)
                val deal = executeTrade(buyOrder, sellOrder)
                deal
            }
            null
        }

        if (deals.isEmpty()) {
            orderBook.placeOrder(newOrder)
        }

        return deals
    }

    private fun executeTrade(buyOrder: Order, sellOrder: Order): Deal {
        val matchedQuantity = minOf(buyOrder.quantity, sellOrder.quantity)
        val sealedDeal = Deal(
            DealId(UUID.randomUUID()), sellOrder.id, buyOrder.id, buyOrder.traderId,
            sellOrder.traderId, buyOrder.assetId,
            matchedQuantity, sellOrder.unitPrice,
            Instant.now(), MatcherType.PRICE_TIME
        )

        orderBook.cancelOrder(buyOrder)
        orderBook.cancelOrder(sellOrder)

        dealRepository.save(sealedDeal)

        return sealedDeal
    }

    private fun prepareOrdersForDeal(
        newOrder: Order,
        bestMatch: Order
    ): Pair<Order, Order> {
        return if (newOrder.type == OrderType.BUY) {
            newOrder to bestMatch
        } else {
            bestMatch to newOrder
        }
    }
}
