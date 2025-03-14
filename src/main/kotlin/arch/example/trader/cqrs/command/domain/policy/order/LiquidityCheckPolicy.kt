package arch.example.trader.cqrs.command.domain.policy.order

import arch.example.trader.component.ddd.PredicatePolicy
import arch.example.trader.component.ddd.and
import arch.example.trader.cqrs.command.domain.entity.Order

class LiquidityCheckPolicy(
    private val orderPriceIsCompliantPolicy: OrderPriceIsCompliantPolicy,
    private val quantityCheckPolicy: QuantityCheckPolicy,
) : PredicatePolicy<Order> {

    override fun invoke(model: Order): Boolean {
        return (orderPriceIsCompliantPolicy and quantityCheckPolicy).invoke(model)
    }
}