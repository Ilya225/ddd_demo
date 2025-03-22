package arch.example.trader.cqrs.query.repository

import arch.example.trader.cqrs.command.domain.entity.AssetId
import arch.example.trader.cqrs.command.domain.entity.AssetType

interface AssetReadRepository {
    fun findAssetType(assetId: AssetId): AssetType?
}