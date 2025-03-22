package arch.example.trader.hexagon.application.port.incoming.dto

import arch.example.trader.hexagon.domain.entity.AssetId
import arch.example.trader.hexagon.domain.entity.OrderType
import arch.example.trader.hexagon.domain.entity.UserId
import arch.example.trader.component.ddd.vo.Money
import java.time.Instant

data class NewOrderRequest(
    val traderId: UserId,
    val assetId: AssetId,
    val type: OrderType,
    val price: Money,
    val quantity: Long,
    val placedAt: Instant
)