package arch.example.trader.layer.domain

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class Wallet(
    val id: UUID,
    val traderId: UUID,
    val balance: BigDecimal,
    val createdAt: Instant,
    val updatedAt: Instant
)