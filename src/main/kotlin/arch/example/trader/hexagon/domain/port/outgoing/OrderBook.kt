package arch.example.trader.hexagon.domain.port.outgoing

import arch.example.trader.hexagon.domain.entity.Order

interface OrderBook {
    fun placeOrder(order: Order): Order
    fun getOrdersByPlacedAtDesc(order: Order): List<Order>
    fun cancelOrder(order: Order)
    fun getMatchingOrders(order: Order): List<Order>
    fun getMarketMakersOrdersBy(order: Order): List<Order>
    fun updateOrder(order: Order): Order
}