package arch.example.trader.cqrs.query.listener.events

import arch.example.trader.cqrs.command.domain.entity.OrderId

data class OrderDiscountsAppliedEvent(
    val orderId: OrderId,
    val appliedDiscounts: Set<DiscountAppliedEvent>
)