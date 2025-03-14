package arch.example.trader.layer.domain

import java.math.BigDecimal
import java.util.UUID

data class IPO(
    val id: UUID,
    val assetId: UUID,
    val price: BigDecimal
)