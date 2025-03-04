package arch.example.trader.cqrs.application.command.domain.entity

import java.util.UUID

@JvmInline
value class OrderId(val id: UUID)

data class Order(
    val id: OrderId
)
