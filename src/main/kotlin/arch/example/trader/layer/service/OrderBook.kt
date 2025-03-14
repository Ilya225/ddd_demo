package arch.example.trader.layer.service

import arch.example.trader.layer.domain.Order
import arch.example.trader.layer.domain.OrderType
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Service
class OrderBook(
) {
    private val buyOrders: ConcurrentHashMap<UUID, PriorityQueue<Order>> = ConcurrentHashMap()
    private val sellOrders: ConcurrentHashMap<UUID, PriorityQueue<Order>> = ConcurrentHashMap()

    fun addOrder(order: Order) {
        when (order.type) {
            OrderType.BUY -> addOrder(
                order,
                buyOrders,
                compareByDescending { it: Order -> it.price }.thenBy { it.placedAt })

            OrderType.SELL -> addOrder(
                order,
                sellOrders,
                compareBy { it: Order -> it.price }.thenBy { it.placedAt })
        }
    }

    private fun addOrder(
        order: Order,
        store: ConcurrentHashMap<UUID, PriorityQueue<Order>>,
        comparator: Comparator<Order>
    ) {
        if (store.containsKey(order.assetId)) {
            store[order.assetId]!!.add(order)
        } else {
            val queue = PriorityQueue<Order>(comparator)
            queue.add(order)
            store.put(order.assetId, queue)
        }
    }

    fun getBestBuyOrder(assetId: UUID): Order? = buyOrders[assetId]?.peek()
    fun getBestSellOrder(assetId: UUID): Order? = sellOrders[assetId]?.peek()

    fun removeOrder(order: Order) {
        when (order.type) {
            OrderType.BUY -> buyOrders[order.assetId]?.remove(order)
            OrderType.SELL -> sellOrders[order.assetId]?.remove(order)
        }
    }

    fun getAllBuyOrders(tradableId: UUID): List<Order> =
        buyOrders[tradableId]?.toList()?.sortedByDescending { it.price } ?: listOf()

    fun getAllSellOrders(tradableId: UUID): List<Order> =
        sellOrders[tradableId]?.toList()?.sortedBy { it.price } ?: listOf()
}
