package arch.example.trader.cqrs.command.domain.repository

import arch.example.trader.cqrs.command.domain.entity.Tradable
import arch.example.trader.cqrs.command.domain.entity.AssetId

interface AssetRepository {
    fun findAsset(id: AssetId): Tradable
}