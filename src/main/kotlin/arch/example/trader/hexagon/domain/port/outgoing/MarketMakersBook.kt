package arch.example.trader.hexagon.domain.port.outgoing

import arch.example.trader.hexagon.domain.entity.Order

interface MarketMakersBook {
    fun getPassiveOrders(): List<Order>
}