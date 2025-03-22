package arch.example.trader.layer.dto

import arch.example.trader.layer.domain.OrderType
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class OrderDto(
    val traderId: UUID,
    val assetId: UUID,
    val unitPrice: BigDecimal,
    val type: OrderType,
    val quantity: Long,
    val placedAt: Instant
)