package arch.example.trader.hexagon.adapters.persistence.inmemory

import arch.example.trader.hexagon.domain.entity.AssetId
import arch.example.trader.hexagon.domain.entity.Order
import arch.example.trader.hexagon.domain.entity.OrderType
import arch.example.trader.hexagon.domain.port.outgoing.OrderBook
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class InMemoryOrderBook : OrderBook {
    private val buyOrders: ConcurrentHashMap<AssetId, PriorityQueue<Order>> = ConcurrentHashMap()
    private val sellOrders: ConcurrentHashMap<AssetId, PriorityQueue<Order>> = ConcurrentHashMap()

    override fun placeOrder(order: Order) {
        when (order.type) {
            OrderType.SELL -> placeSellOrder(order)
            OrderType.BUY -> placeBuyOrder(order)
        }
    }

    private fun placeSellOrder(order: Order) {
        if (sellOrders.containsKey(order.assetId)) {
            sellOrders[order.assetId]!!.add(order)
        } else {
            sellOrders.put(
                order.assetId,
                PriorityQueue<Order>(compareBy { it: Order -> it.unitPrice.amount }.thenBy { it.placedAt })
            )
        }
    }

    private fun placeBuyOrder(order: Order) {
        if (buyOrders.contains(order.assetId)) {
            buyOrders[order.assetId]!!.add(order)
        } else {
            buyOrders.put(
                order.assetId,
                PriorityQueue<Order>((compareByDescending { it: Order -> it.unitPrice.amount }).thenBy { it.placedAt })
            )
        }
    }

    override fun getMatchingOrders(order: Order): List<Order> =
        when (order.type) {
            OrderType.SELL -> sellOrders[order.assetId]?.toList() ?: listOf()
            OrderType.BUY -> buyOrders[order.assetId]?.toList() ?: listOf()
        }


    override fun getBestOrder(order: Order): Order? =
        when (order.type) {
            OrderType.SELL -> getBestSellOrder(order.assetId)
            OrderType.BUY -> getBestBuyOrder(order.assetId)
        }

    private fun getBestBuyOrder(assetId: AssetId): Order? =
        buyOrders[assetId]?.peek()


    private fun getBestSellOrder(assetId: AssetId): Order? =
        sellOrders[assetId]?.peek()


    override fun cancelOrder(order: Order) {
        when (order.type) {
            OrderType.SELL -> sellOrders[order.assetId]?.removeAll { it: Order -> order.id == it.id  }
            OrderType.BUY -> buyOrders[order.assetId]?.removeAll { it: Order -> order.id == it.id  }
        }
    }
}