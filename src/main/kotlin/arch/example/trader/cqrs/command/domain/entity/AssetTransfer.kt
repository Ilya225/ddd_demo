package arch.example.trader.cqrs.command.domain.entity

import java.util.UUID

@JvmInline
value class AssetTransferId(val id: UUID)

data class AssetTransfer(
    val id: AssetTransferId,
    val dealId: DealId,
    val assetId: AssetId,
    val sellerId: UserId,
    val buyerId: UserId,
    val quantity: Long
)
