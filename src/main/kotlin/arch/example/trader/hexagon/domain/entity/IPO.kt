package arch.example.trader.hexagon.domain.entity

import arch.example.trader.component.ddd.vo.Money
import java.util.UUID

@JvmInline
value class IPOId(val id: UUID)

data class IPO(
    val id: IPOId,
    val assetId: AssetId,
    val price: Money
)