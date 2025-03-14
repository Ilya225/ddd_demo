package arch.example.trader.hexagon.domain.entity

import java.time.Instant

data class Stock(
    override val id: AssetId,
    val code: String,
    val updatedAt: Instant
) : Asset