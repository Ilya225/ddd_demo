package arch.example.trader.cqrs.query

import arch.example.trader.component.ddd.vo.Money
import arch.example.trader.cqrs.command.domain.entity.AssetId
import arch.example.trader.cqrs.command.domain.entity.DealId
import arch.example.trader.cqrs.command.domain.entity.UserId

data class CalculateFeeQuery(
    val dealId: DealId,
    val buyerId: UserId,
    val sellerId: UserId,
    val assetId: AssetId,
    val quantity: Long,
    val unitPrice: Money
)