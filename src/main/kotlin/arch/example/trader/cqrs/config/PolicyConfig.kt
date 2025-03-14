package arch.example.trader.cqrs.config

import arch.example.trader.cqrs.command.domain.policy.MarketIsOpenPolicy
import arch.example.trader.cqrs.command.domain.policy.OrderPlacementPolicy
import arch.example.trader.cqrs.command.domain.policy.order.LiquidityCheckPolicy
import arch.example.trader.cqrs.command.domain.policy.order.OrderPriceIsCompliantPolicy
import arch.example.trader.cqrs.command.domain.policy.order.QuantityCheckPolicy
import arch.example.trader.cqrs.command.domain.policy.order.UserHasSufficientFundsToBuyPolicy
import arch.example.trader.cqrs.command.domain.repository.DealRepository
import arch.example.trader.cqrs.command.domain.repository.IPORepository
import arch.example.trader.cqrs.command.domain.repository.WalletRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PolicyConfig {

    @Bean
    fun liquidityCheckPolicy(dealRepository: DealRepository, ipoRepository: IPORepository): LiquidityCheckPolicy =
        LiquidityCheckPolicy(
            OrderPriceIsCompliantPolicy(dealRepository, ipoRepository),
            QuantityCheckPolicy()
        )

    @Bean
    fun orderPlacementPolicy(
        liquidityCheckPolicy: LiquidityCheckPolicy,
        walletRepository: WalletRepository
    ): OrderPlacementPolicy =
        OrderPlacementPolicy(
            MarketIsOpenPolicy(),
            liquidityCheckPolicy,
            UserHasSufficientFundsToBuyPolicy(walletRepository)
        )
}