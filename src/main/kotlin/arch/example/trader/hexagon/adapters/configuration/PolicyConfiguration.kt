package arch.example.trader.hexagon.adapters.configuration

import arch.example.trader.hexagon.domain.policy.AssetExistsPolicy
import arch.example.trader.hexagon.domain.policy.MarketIsOpenPolicy
import arch.example.trader.hexagon.domain.policy.OrderPlacementPolicy
import arch.example.trader.hexagon.domain.policy.order.OrderPriceIsCompliantPolicy
import arch.example.trader.hexagon.domain.policy.order.QuantityCheckPolicy
import arch.example.trader.hexagon.domain.policy.order.UserHasSufficientFundsToBuyPolicy
import arch.example.trader.hexagon.domain.port.outgoing.AssetRepository
import arch.example.trader.hexagon.domain.port.outgoing.DealRepository
import arch.example.trader.hexagon.domain.port.outgoing.IPORepository
import arch.example.trader.hexagon.domain.port.outgoing.WalletRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PolicyConfiguration {

    @Bean
    fun orderPlacementPolicy(
        marketIsOpenPolicy: MarketIsOpenPolicy,
        dealRepository: DealRepository,
        ipoRepository: IPORepository,
        walletRepository: WalletRepository,
        assetRepository: AssetRepository
    ): OrderPlacementPolicy {
        return OrderPlacementPolicy(
            OrderPriceIsCompliantPolicy(dealRepository, ipoRepository),
            marketIsOpenPolicy,
            QuantityCheckPolicy(),
            AssetExistsPolicy(assetRepository),
            UserHasSufficientFundsToBuyPolicy(walletRepository)

        )
    }

    @Bean
    fun marketIsOpenPolicy(): MarketIsOpenPolicy {
        return MarketIsOpenPolicy()
    }
}