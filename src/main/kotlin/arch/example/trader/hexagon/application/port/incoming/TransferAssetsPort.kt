package arch.example.trader.hexagon.application.port.incoming

import arch.example.trader.hexagon.domain.entity.AssetTransfer
import arch.example.trader.hexagon.domain.entity.Deal
import arch.example.trader.hexagon.domain.entity.Order

interface TransferAssetsPort {
    fun transferAsset(order: Order, deals: List<Deal>): List<AssetTransfer>
}
