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
        val orders = orderBook.getOrdersByPlacedAtDesc(newOrder)

        val deals = mutableListOf<Deal>()
        var remainingQuantity = newOrder.quantity

        for (matchOrder in orders) {

            if (remainingQuantity > 0 && newOrder.isMatch(matchOrder)) {

                val tradeQuantity = minOf(remainingQuantity, matchOrder.quantity)
                val (buyOrder, sellOrder) = prepareOrdersForDeal(newOrder, matchOrder)

                val allocatedOrder = matchOrder.allocate(tradeQuantity)
                orderBook.updateOrder(allocatedOrder)

                val deal = executeTrade(buyOrder.copy(quantity = tradeQuantity), sellOrder)

                deals.add(deal)
                remainingQuantity -= tradeQuantity
            }
        }

        if (remainingQuantity > 0) {
            orderBook.placeOrder(newOrder.allocate(newOrder.quantity - remainingQuantity))
        }

        return deals
    }

    private fun executeTrade(buyOrder: Order, sellOrder: Order): Deal {
        val matchedQuantity = minOf(buyOrder.quantity, sellOrder.quantity)
        val sealedDeal = Deal(
            DealId(UUID.randomUUID()),
            sellOrder.id,
            sellOrder.traderId,
            buyOrder.id,
            buyOrder.traderId,
            buyOrder.assetId,
            matchedQuantity,
            sellOrder.price,
            sellOrder.unitPrice,
            Instant.now()
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
