package arch.example.trader.cqrs.application.command.domain.entity

import java.util.UUID


@JvmInline
value class TriggerId(val id: UUID)

data class Trigger(
    val id: TriggerId
)