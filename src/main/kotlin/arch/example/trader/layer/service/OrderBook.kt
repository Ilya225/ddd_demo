package arch.example.trader.layer.service

import arch.example.trader.layer.entity.Order
import arch.example.trader.layer.entity.OrderType
import org.springframework.stereotype.Service
import java.util.PriorityQueue
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Service
class OrderBook(
) {
    private val buyOrders: ConcurrentHashMap<UUID, PriorityQueue<Order>> = ConcurrentHashMap()
    private val sellOrders: ConcurrentHashMap<UUID, PriorityQueue<Order>> = ConcurrentHashMap()

    fun addOrder(order: Order) {
        when (order.type) {
            OrderType.BUY -> addOrder(order, buyOrders, compareByDescending { it.price })
            OrderType.SELL -> addOrder(order, sellOrders, compareBy { it.price })
        }
    }

    private fun addOrder(
        order: Order,
        store: ConcurrentHashMap<UUID, PriorityQueue<Order>>,
        comparator: Comparator<Order>
    ) {
        if (store.contains(order.id)) {
            store[order.id]!!.add(order)
        } else {
            store.put(order.id, PriorityQueue<Order>(comparator))
        }
    }

    fun getBestBuyOrder(assetId: UUID): Order? = buyOrders[assetId]?.peek()
    fun getBestSellOrder(assetId: UUID): Order? = sellOrders[assetId]?.peek()

    fun removeOrder(order: Order) {
        when (order.type) {
            OrderType.BUY -> buyOrders[order.id]?.remove(order)
            OrderType.SELL -> sellOrders[order.id]?.remove(order)
        }
    }

    fun getAllBuyOrders(tradableId: UUID): List<Order> =
        buyOrders[tradableId]?.toList()?.sortedByDescending { it.price } ?: listOf()

    fun getAllSellOrders(tradableId: UUID): List<Order> =
        sellOrders[tradableId]?.toList()?.sortedBy { it.price } ?: listOf()
}
