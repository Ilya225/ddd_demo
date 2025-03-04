package arch.example.trader.hexagon.domain.entity

import java.util.UUID

@JvmInline
value class DealId(val id: UUID)

data class Deal(
    val dealId: DealId
)