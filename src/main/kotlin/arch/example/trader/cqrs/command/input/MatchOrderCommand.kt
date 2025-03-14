package arch.example.trader.cqrs.command.input

import arch.example.trader.cqrs.command.domain.entity.OrderId

data class MatchOrderCommand(
    val id: OrderId
)
