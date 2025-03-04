package arch.example.trader.layer.entity

import java.math.BigDecimal
import java.util.UUID

data class IPO(
    val id: UUID,
    val assetId: UUID,
    val price: BigDecimal
)