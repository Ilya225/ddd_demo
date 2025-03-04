package arch.example.trader.hexagon.domain.port.incoming

import arch.example.trader.hexagon.domain.entity.Order

interface PlaceOrderPort {
    fun placeOrder(order: Order)
}