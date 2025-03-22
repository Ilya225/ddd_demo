package arch.example.trader.cqrs.command.output

import arch.example.trader.cqrs.command.domain.entity.DealId
import arch.example.trader.cqrs.command.domain.entity.OrderId

data class OrderMatched(
    val orderId: OrderId,
    val dealId: List<DealId>
)
