package arch.example.trader.cqrs.command.domain.policy

import arch.example.trader.component.ddd.PredicatePolicy
import arch.example.trader.component.ddd.and
import arch.example.trader.cqrs.command.domain.entity.Order
import arch.example.trader.cqrs.command.domain.policy.order.LiquidityCheckPolicy
import arch.example.trader.cqrs.command.domain.policy.order.UserHasSufficientFundsToBuyPolicy

class OrderPlacementPolicy(
    private val marketIsOpenPolicy: MarketIsOpenPolicy,
    private val liquidityCheckPolicy: LiquidityCheckPolicy,
    private val userHasSufficientFundsToBuyPolicy: UserHasSufficientFundsToBuyPolicy
) : PredicatePolicy<Order> {

    override fun invoke(model: Order): Boolean =
        (liquidityCheckPolicy and
                PredicatePolicy<Order> { marketIsOpenPolicy.invoke(model.placedAt) }
                and userHasSufficientFundsToBuyPolicy)
            .invoke(model)
}