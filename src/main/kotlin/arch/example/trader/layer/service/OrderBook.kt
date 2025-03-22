package arch.example.trader.layer.service

import arch.example.trader.layer.domain.Order
import java.util.*

interface OrderBook {
    fun addOrder(order: Order)
    fun getBestBuyOrder(assetId: UUID): Order?
    fun getBestSellOrder(assetId: UUID): Order?
    fun getMarketMakersOrders(assetId: UUID): List<Order>
    fun removeOrder(order: Order)
    fun getAllBuyOrders(assetId: UUID): List<Order>
    fun getAllSellOrders(assetId: UUID): List<Order>
}
