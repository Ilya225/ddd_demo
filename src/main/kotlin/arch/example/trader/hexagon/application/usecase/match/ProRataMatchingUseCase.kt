package arch.example.trader.hexagon.application.usecase.match

import arch.example.trader.hexagon.application.port.incoming.MatchOrderPort
import arch.example.trader.hexagon.domain.entity.Deal
import arch.example.trader.hexagon.domain.entity.DealId
import arch.example.trader.hexagon.domain.entity.Order
import arch.example.trader.hexagon.domain.entity.OrderType
import arch.example.trader.hexagon.domain.port.outgoing.DealRepository
import arch.example.trader.hexagon.domain.port.outgoing.OrderBook
import java.time.Instant
import java.util.UUID

class ProRataMatchingUseCase(
    private val orderBook: OrderBook,
    private val dealRepository: DealRepository
) : MatchOrderPort {

    override fun matchOrder(newOrder: Order): List<Deal> {
        val matchingOrders = orderBook.getMatchingOrders(newOrder)
            .filter { order -> newOrder.isMatch(order) }

        val totalQuantity = matchingOrders.sumOf { it.quantity }
        val allocatedOrders = matchingOrders.map { order ->
            val allocation = (order.quantity.toDouble() / totalQuantity) * newOrder.quantity
            order.copy(quantity = allocation.toLong())
        }

        val deals = executeTrades(newOrder, allocatedOrders)
        return deals
    }

    private fun executeTrades(
        order: Order,
        orders: List<Order>
    ): List<Deal> {
        val sealedDeals = orders.map {
            val (buyOrder, sellOrder) = prepareOrdersForDeal(order, it)
            val sealedDeal = Deal(
                DealId(UUID.randomUUID()), sellOrder.id, buyOrder.id, buyOrder.assetId,
                it.quantity, sellOrder.price, Instant.now()
            )
            sealedDeal
        }

        dealRepository.saveAll(sealedDeals)
        return sealedDeals
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