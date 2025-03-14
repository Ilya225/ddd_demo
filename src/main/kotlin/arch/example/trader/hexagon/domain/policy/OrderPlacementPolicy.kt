package arch.example.trader.hexagon.domain.policy

import arch.example.trader.component.ddd.PredicatePolicy
import arch.example.trader.component.ddd.and
import arch.example.trader.hexagon.domain.entity.Order
import arch.example.trader.hexagon.domain.policy.order.OrderPriceIsCompliantPolicy
import arch.example.trader.hexagon.domain.policy.order.QuantityCheckPolicy
import arch.example.trader.hexagon.domain.policy.order.UserHasSufficientFundsToBuyPolicy

class OrderPlacementPolicy(
    private val orderPriceIsCompliantPolicy: OrderPriceIsCompliantPolicy,
    private val marketIsOpenPolicy: MarketIsOpenPolicy,
    private val quantityCheckPolicy: QuantityCheckPolicy,
    private val assetExistsPolicy: AssetExistsPolicy,
    private val userHasSufficientFundsToBuyPolicy: UserHasSufficientFundsToBuyPolicy
) : PredicatePolicy<Order> {

    override fun invoke(model: Order): Boolean =
        (orderPriceIsCompliantPolicy
                and PredicatePolicy { assetExistsPolicy.invoke(model.assetId) }
                and PredicatePolicy { marketIsOpenPolicy.invoke(model.placedAt) }
                and quantityCheckPolicy
                and userHasSufficientFundsToBuyPolicy)
            .invoke(model)
}