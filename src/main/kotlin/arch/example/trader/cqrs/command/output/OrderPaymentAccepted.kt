package arch.example.trader.cqrs.command.output

import arch.example.trader.cqrs.command.domain.entity.OrderId

data class OrderPaymentAccepted(
    val orderId: OrderId
)
