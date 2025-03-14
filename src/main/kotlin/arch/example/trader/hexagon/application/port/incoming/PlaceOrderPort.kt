package arch.example.trader.hexagon.application.port.incoming

import arch.example.trader.hexagon.application.port.incoming.dto.PlaceOrderRequest

interface PlaceOrderPort {
    fun placeOrder(request: PlaceOrderRequest)
}