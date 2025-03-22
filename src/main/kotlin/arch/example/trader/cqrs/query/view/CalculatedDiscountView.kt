package arch.example.trader.cqrs.query.view

import arch.example.trader.component.ddd.vo.Money

interface CalculatedDiscountView {
    val buyerFeeAfterDiscount: Money
    val sellerFeeAfterDiscount: Money
}
