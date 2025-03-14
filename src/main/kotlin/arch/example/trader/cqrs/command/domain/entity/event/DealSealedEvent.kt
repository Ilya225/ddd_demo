package arch.example.trader.cqrs.command.domain.entity.event

import arch.example.trader.cqrs.command.domain.entity.DealId

data class DealSealedEvent(
    val dealId: DealId
)