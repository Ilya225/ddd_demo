package arch.example.trader.layer.domain

import java.util.UUID

data class Portfolio(
    val id: UUID,
    val traderId: UUID,
    val assets: MutableMap<UUID, Long> = mutableMapOf()
)