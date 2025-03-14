package arch.example.trader.hexagon.domain.port.outgoing

import arch.example.trader.hexagon.domain.entity.Order

interface OrderBook {
    fun placeOrder(order: Order)
    fun getBestOrder(order: Order): Order?
    fun cancelOrder(order: Order)
    fun getMatchingOrders(order: Order): List<Order>
}