package arch.example.trader.hexagon.domain.port.outgoing

import arch.example.trader.hexagon.domain.entity.AssetId

interface AssetRepository {
    fun assetExists(id: AssetId): Boolean
}