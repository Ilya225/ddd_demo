package arch.example.trader.cqrs.config

import arch.example.trader.cqrs.command.domain.entity.AssetType
import arch.example.trader.cqrs.command.strategy.MarketMakerMatchingStrategy
import arch.example.trader.cqrs.command.strategy.OrderMatchingStrategy
import arch.example.trader.cqrs.command.strategy.PriceTimeMatchingStrategy
import arch.example.trader.cqrs.command.strategy.ProRataMatchingStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TradingFlowConfig {

    @Bean
    fun matchingStrategies(strategies: List<OrderMatchingStrategy>): Map<AssetType, OrderMatchingStrategy> =
        mapOf(
            AssetType.COMMODITY to strategies.find { it -> it is ProRataMatchingStrategy }!!,
            AssetType.FUTURE to strategies.find { it -> it is MarketMakerMatchingStrategy }!!,
            AssetType.OPTION to strategies.find { it -> it is MarketMakerMatchingStrategy }!!,
            AssetType.STOCK to strategies.find { it -> it is PriceTimeMatchingStrategy }!!,
            AssetType.CRYPTO to strategies.find { it -> it is MarketMakerMatchingStrategy }!!,
            AssetType.FOREX to strategies.find { it -> it is MarketMakerMatchingStrategy }!!
        )
}