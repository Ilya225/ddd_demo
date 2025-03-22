package arch.example.trader.cqrs.query.listener.events

import arch.example.trader.cqrs.command.domain.entity.OrderId

data class OrderFeeCalculatedEvent(
    val orderId: OrderId,
    val feesData: Set<FeeCalculatedEvent>
)