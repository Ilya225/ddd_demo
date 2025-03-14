package arch.example.trader.cqrs.command.strategy

import arch.example.trader.cqrs.command.domain.entity.Deal
import arch.example.trader.cqrs.command.domain.entity.DealId
import arch.example.trader.cqrs.command.domain.entity.Order
import arch.example.trader.cqrs.command.domain.entity.OrderType
import arch.example.trader.cqrs.command.domain.repository.DealRepository
import arch.example.trader.cqrs.command.domain.repository.OrderBook
import java.time.Instant
import java.util.*

class ProRataMatchingStrategy(
    private val orderBook: OrderBook,
    private val dealRepository: DealRepository
) : OrderMatchingStrategy {

    override fun invoke(newOrder: Order): List<Deal> {
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