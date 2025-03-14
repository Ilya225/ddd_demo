package arch.example.trader.hexagon.domain.port.outgoing

import arch.example.trader.hexagon.domain.entity.AssetId
import arch.example.trader.hexagon.domain.entity.Deal
import arch.example.trader.hexagon.domain.entity.DealId


interface DealRepository {
    fun findByAssetIdOrderByCreatedAtDesc(id: AssetId): Deal?

    fun save(deal: Deal): DealId
    fun saveAll(deals: List<Deal>)
}