package arch.example.trader.cqrs.command.domain.entity.event

import arch.example.trader.cqrs.command.domain.entity.OrderId

data class OrderMatchedEvent(
    val orderId: OrderId,
    val sealedDeals: Set<DealSealedEvent>
)