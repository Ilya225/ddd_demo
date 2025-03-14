package arch.example.trader.hexagon.application.usecase.match

import arch.example.trader.hexagon.application.port.incoming.MatchOrderPort
import arch.example.trader.hexagon.domain.entity.Deal
import arch.example.trader.hexagon.domain.entity.DealId
import arch.example.trader.hexagon.domain.entity.Order
import arch.example.trader.hexagon.domain.entity.OrderType
import arch.example.trader.hexagon.domain.port.outgoing.DealRepository
import arch.example.trader.hexagon.domain.port.outgoing.OrderBook
import java.time.Instant
import java.util.*

class PriceTimeMatchingUseCase(
    private val orderBook: OrderBook,
    private val dealRepository: DealRepository
) : MatchOrderPort {

    override fun matchOrder(newOrder: Order): List<Deal> {
        val bestMatch = orderBook.getBestOrder(newOrder)

        if (bestMatch != null && newOrder.isMatch(bestMatch)) {
            val (buyOrder, sellOrder) = prepareOrdersForDeal(newOrder, bestMatch)
            val deal = executeTrade(buyOrder, sellOrder)
            return listOf(deal)
        }
        orderBook.placeOrder(newOrder)
        return emptyList()
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
