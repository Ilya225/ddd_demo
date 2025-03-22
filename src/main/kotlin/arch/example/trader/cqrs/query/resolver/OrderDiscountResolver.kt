package arch.example.trader.cqrs.query.resolver

import arch.example.trader.component.cqrs.resolver.QueryResolver
import arch.example.trader.cqrs.query.GetDiscountQuery
import arch.example.trader.cqrs.query.view.CalculatedDiscountView

class OrderDiscountResolver : QueryResolver<GetDiscountQuery, CalculatedDiscountView> {

    override fun resolve(query: GetDiscountQuery): CalculatedDiscountView {
        TODO("Not yet implemented")
    }
}