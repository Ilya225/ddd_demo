package arch.example.trader.cqrs.command.input

import arch.example.trader.cqrs.command.domain.entity.OrderId

data class OrderPaymentCommand(
    val orderId: OrderId,
    val dealsPayments: Set<DealPaymentCommand>
)
