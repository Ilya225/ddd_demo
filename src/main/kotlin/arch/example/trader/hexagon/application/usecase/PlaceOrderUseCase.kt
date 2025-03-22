package arch.example.trader.hexagon.application.usecase

import arch.example.trader.hexagon.application.port.incoming.PlaceOrderPort
import arch.example.trader.hexagon.application.port.incoming.dto.NewOrderRequest
import arch.example.trader.hexagon.domain.entity.Order
import arch.example.trader.hexagon.domain.entity.OrderId
import arch.example.trader.hexagon.domain.policy.OrderPlacementPolicy
import arch.example.trader.hexagon.domain.port.outgoing.OrderBook
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PlaceOrderUseCase(
    private val orderBook: OrderBook,
    private val orderPlacementPolicy: OrderPlacementPolicy
): PlaceOrderPort {

    override fun placeOrder(request: NewOrderRequest): Order {

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
            return orderBook.placeOrder(newOrder)
        }

        throw RuntimeException("Can't create the order")
    }
}