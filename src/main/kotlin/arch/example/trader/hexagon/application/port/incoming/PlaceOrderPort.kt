package arch.example.trader.hexagon.application.port.incoming

import arch.example.trader.hexagon.application.port.incoming.dto.NewOrderRequest
import arch.example.trader.hexagon.domain.entity.Order

interface PlaceOrderPort {
    fun placeOrder(request: NewOrderRequest): Order
}