package arch.example.trader.cqrs.query.view

import arch.example.trader.component.ddd.vo.Money

interface OrderFeesView {
    val buyerFee: Money
    val sellerFee: Money
    val totalPrice: Money
}