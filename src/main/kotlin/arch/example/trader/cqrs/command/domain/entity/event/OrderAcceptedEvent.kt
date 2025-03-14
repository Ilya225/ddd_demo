package arch.example.trader.cqrs.command.domain.entity.event

import arch.example.trader.cqrs.command.domain.entity.OrderId

data class OrderAcceptedEvent(
    val orderId: OrderId
)