package arch.example.trader.hexagon.domain.port.outgoing

import arch.example.trader.hexagon.domain.entity.AssetId
import arch.example.trader.hexagon.domain.entity.Order

interface OrderBook {

    fun placeBuyOrder(order: Order)

    fun placeSellOrder(order: Order)

    fun getBestBuyOrder(assetId: AssetId): Order?
    fun getBestSellOrder(assetId: AssetId): Order?

    fun cancelOrder(order: Order)
}