package arch.example.trader.hexagon.domain.entity

import java.math.BigDecimal
import java.time.Instant

data class Stock(
    override val id: AssetId,
    val code: String,
    val price: BigDecimal,
    val updatedAt: Instant
    ) : Asset