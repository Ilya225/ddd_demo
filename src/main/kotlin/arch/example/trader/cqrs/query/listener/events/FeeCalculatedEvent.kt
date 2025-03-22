package arch.example.trader.cqrs.query.listener.events

import arch.example.trader.component.ddd.vo.Money
import arch.example.trader.cqrs.command.domain.entity.AssetId
import arch.example.trader.cqrs.command.domain.entity.DealId
import arch.example.trader.cqrs.command.domain.entity.UserId

data class FeeCalculatedEvent(
    val dealId: DealId,
    val sellerId: UserId,
    val buyerId: UserId,
    val assetId: AssetId,
    val totalPrice: Money,
    val buyerFee: Money,
    val sellerFee: Money,
)