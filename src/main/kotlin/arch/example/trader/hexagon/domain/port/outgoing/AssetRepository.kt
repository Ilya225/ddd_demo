package arch.example.trader.hexagon.domain.port.outgoing

import arch.example.trader.hexagon.domain.entity.AssetId
import arch.example.trader.hexagon.domain.entity.AssetType

interface AssetRepository {
    fun assetExists(id: AssetId): Boolean
    fun findAssetType(id: AssetId): AssetType?
}