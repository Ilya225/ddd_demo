package arch.example.trader.cqrs.command.output

import arch.example.trader.cqrs.command.domain.entity.DealId

data class OrderMatched(
    val dealId: List<DealId>
)
