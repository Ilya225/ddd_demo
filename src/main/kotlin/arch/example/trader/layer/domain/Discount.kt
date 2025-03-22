package arch.example.trader.layer.domain

import arch.example.trader.hexagon.domain.entity.UserId
import java.math.BigDecimal
import java.util.UUID

data class Discount(
    val id: UUID,
    val userId: UserId,
    val type: OrderType,
    val discountRatio: BigDecimal
)