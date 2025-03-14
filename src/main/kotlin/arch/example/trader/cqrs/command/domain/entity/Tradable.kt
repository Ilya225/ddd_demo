package arch.example.trader.cqrs.command.domain.entity

import java.util.UUID

@JvmInline
value class AssetId(val id: UUID)

interface Tradable {
    val id: AssetId
    val type: AssetType
}