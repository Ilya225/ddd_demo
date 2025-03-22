package arch.example.trader.layer.service

import arch.example.trader.layer.domain.Order
import arch.example.trader.layer.domain.OrderType
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Service
class OrderBookService(
) : OrderBook {
    private val buyOrders: ConcurrentHashMap<UUID, PriorityQueue<Order>> = ConcurrentHashMap()
    private val sellOrders: ConcurrentHashMap<UUID, PriorityQueue<Order>> = ConcurrentHashMap()

    override fun addOrder(order: Order) {
        when (order.type) {
            OrderType.BUY -> addOrder(
                order,
                buyOrders,
                compareByDescending { it: Order -> it.unitPrice }.thenBy { it.placedAt })

            OrderType.SELL -> addOrder(
                order,
                sellOrders,
                compareBy { it: Order -> it.unitPrice }.thenBy { it.placedAt })
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

    override fun getBestBuyOrder(assetId: UUID): Order? = buyOrders[assetId]?.peek()
    override fun getBestSellOrder(assetId: UUID): Order? = sellOrders[assetId]?.peek()

    override fun getMarketMakersOrders(assetId: UUID): List<Order> =
        TODO()

    override fun removeOrder(order: Order) {
        when (order.type) {
            OrderType.BUY -> buyOrders[order.assetId]?.remove(order)
            OrderType.SELL -> sellOrders[order.assetId]?.remove(order)
        }
    }

    override fun getAllBuyOrders(assetId: UUID): List<Order> =
        buyOrders[assetId]?.toList()?.sortedByDescending { it.unitPrice } ?: listOf()

    override fun getAllSellOrders(assetId: UUID): List<Order> =
        sellOrders[assetId]?.toList()?.sortedBy { it.unitPrice } ?: listOf()
}
