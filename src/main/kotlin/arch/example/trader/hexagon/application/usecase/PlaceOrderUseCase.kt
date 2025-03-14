package arch.example.trader.hexagon.application.usecase

import arch.example.trader.hexagon.application.port.incoming.MatchOrderPort
import arch.example.trader.hexagon.application.port.incoming.PlaceOrderPort
import arch.example.trader.hexagon.application.port.incoming.dto.PlaceOrderRequest
import arch.example.trader.hexagon.domain.entity.Order
import arch.example.trader.hexagon.domain.entity.OrderId
import arch.example.trader.hexagon.domain.entity.OrderType
import arch.example.trader.hexagon.domain.policy.OrderPlacementPolicy
import arch.example.trader.hexagon.domain.port.outgoing.OrderBook
import java.util.UUID

class PlaceOrderUseCase(
    private val orderMatcher: MatchOrderPort,
    private val orderPlacementPolicy: OrderPlacementPolicy
): PlaceOrderPort {

    override fun placeOrder(request: PlaceOrderRequest) {

        val newOrder = Order(
            OrderId(UUID.randomUUID()),
            request.traderId,
            request.assetId,
            request.type,
            request.price,
            request.quantity,
            request.placedAt
        )

        if (orderPlacementPolicy.invoke(newOrder)) {
            orderMatcher.matchOrder(newOrder)
            return
        }

        throw RuntimeException("Can't create the order")
    }
}