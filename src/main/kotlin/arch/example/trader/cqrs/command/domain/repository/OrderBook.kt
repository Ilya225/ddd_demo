package arch.example.trader.cqrs.command.domain.repository

import arch.example.trader.cqrs.command.domain.entity.Order
import arch.example.trader.cqrs.command.domain.entity.OrderId

interface OrderBook {
    fun findOrder(id: OrderId): Order?
    fun placeOrder(order: Order)
    fun getBestOrder(order: Order): Order?
    fun cancelOrder(order: Order)
    fun getMatchingOrders(order: Order): List<Order>
    fun getPassiveOrders(): List<Order>
}