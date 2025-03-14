package arch.example.trader.hexagon.domain.entity

import java.math.BigDecimal
import java.time.Instant

data class FutureContract(
    override val id: AssetId,
    val code: String,
    val price: BigDecimal,
    val expireAt: Instant,
    val updatedAt: Instant
) : Asset