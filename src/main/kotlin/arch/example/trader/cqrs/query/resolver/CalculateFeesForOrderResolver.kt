package arch.example.trader.cqrs.query.resolver

import arch.example.trader.component.cqrs.resolver.QueryResolver
import arch.example.trader.cqrs.query.CalculateFeeQuery
import arch.example.trader.cqrs.query.strategy.FeeCalculateStrategy
import arch.example.trader.cqrs.query.view.OrderFeesView
import org.springframework.stereotype.Service

@Service
class CalculateFeesForOrderResolver(
    private val feeCalculateStrategy: FeeCalculateStrategy
) : QueryResolver<CalculateFeeQuery, OrderFeesView> {

    override fun resolve(query: CalculateFeeQuery): OrderFeesView =
        feeCalculateStrategy.invoke(query)
}