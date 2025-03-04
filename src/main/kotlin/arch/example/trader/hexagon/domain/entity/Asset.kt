package arch.example.trader.hexagon.domain.entity

import java.util.UUID

@JvmInline
value class AssetId(val id: UUID)

interface Asset {
    val id: AssetId
}

