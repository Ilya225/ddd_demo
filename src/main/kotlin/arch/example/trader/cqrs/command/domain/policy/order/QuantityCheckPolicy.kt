package arch.example.trader.cqrs.command.domain.policy.order

import arch.example.trader.component.ddd.PredicatePolicy
import arch.example.trader.cqrs.command.domain.entity.Order
import kotlin.ranges.contains

class QuantityCheckPolicy : PredicatePolicy<Order> {

    override fun invoke(model: Order): Boolean =
        quantityIsValid(model)


    fun quantityIsValid(model: Order): Boolean {
        val minSize = 1
        val maxSize = 500

        return model.quantity in minSize..maxSize
    }
}