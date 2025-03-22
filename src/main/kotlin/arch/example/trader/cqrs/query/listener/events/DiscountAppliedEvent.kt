package arch.example.trader.cqrs.query.listener.events

import arch.example.trader.component.ddd.vo.Money
import arch.example.trader.cqrs.command.domain.entity.AssetId
import arch.example.trader.cqrs.command.domain.entity.DealId
import arch.example.trader.cqrs.command.domain.entity.UserId

data class DiscountAppliedEvent(
    val dealId: DealId,
    val buyerId: UserId,
    val sellerId: UserId,
    val assetId: AssetId,
    val buyerFeeAfterDiscount: Money,
    val sellerFeeAfterDiscount: Money
)