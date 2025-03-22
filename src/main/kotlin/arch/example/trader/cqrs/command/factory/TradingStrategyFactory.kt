package arch.example.trader.cqrs.command.factory

import arch.example.trader.cqrs.command.domain.entity.AssetType
import arch.example.trader.cqrs.command.domain.entity.Order
import arch.example.trader.cqrs.command.domain.policy.matching.OrderMatchingStrategy
import arch.example.trader.cqrs.command.domain.repository.AssetRepository

class TradingStrategyFactory(
    private val assetRepository: AssetRepository,
    private val strategies: Map<AssetType, OrderMatchingStrategy>
) {

    fun obtainStrategy(order: Order): OrderMatchingStrategy {
        val asset = assetRepository.findAsset(order.assetId)

        val strategy =
            strategies[asset.type] ?: throw RuntimeException("No suitable trading strategy for order: ${order.id}")

        return strategy
    }
}
