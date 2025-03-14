package arch.example.trader.cqrs.command.domain.repository

import arch.example.trader.cqrs.command.domain.entity.AssetId
import arch.example.trader.cqrs.command.domain.entity.Deal
import arch.example.trader.cqrs.command.domain.entity.DealId


interface DealRepository {
    fun findByAssetIdOrderByCreatedAtDesc(id: AssetId): Deal?

    fun save(deal: Deal): DealId
    fun saveAll(deals: List<Deal>)
}