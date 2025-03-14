package arch.example.trader.layer.domain

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class Deal(
    val id: UUID,
    val sellOrderId: UUID,
    val buyOrderId: UUID,
    val assetId: UUID,
    val quantity: Long,
    val price: BigDecimal,
    val createdAt: Instant,
)