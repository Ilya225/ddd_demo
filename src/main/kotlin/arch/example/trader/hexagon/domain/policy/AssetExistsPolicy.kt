package arch.example.trader.hexagon.domain.policy

import arch.example.trader.component.ddd.PredicatePolicy
import arch.example.trader.hexagon.domain.entity.AssetId
import arch.example.trader.hexagon.domain.port.outgoing.AssetRepository

class AssetExistsPolicy(
    val assetRepository: AssetRepository
) : PredicatePolicy<AssetId> {

    override fun invoke(model: AssetId): Boolean =
        assetRepository.assetExists(model)
}