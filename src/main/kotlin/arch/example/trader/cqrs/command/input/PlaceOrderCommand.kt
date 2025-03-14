package arch.example.trader.cqrs.command.input

import arch.example.trader.component.ddd.vo.Money
import arch.example.trader.cqrs.command.domain.entity.AssetId
import arch.example.trader.cqrs.command.domain.entity.OrderType
import arch.example.trader.cqrs.command.domain.entity.UserId
import java.time.Instant

data class PlaceOrderCommand(
    val traderId: UserId,
    val assetId: AssetId,
    val type: OrderType,
    val price: Money,
    val quantity: Long,
    val placedAt: Instant
)