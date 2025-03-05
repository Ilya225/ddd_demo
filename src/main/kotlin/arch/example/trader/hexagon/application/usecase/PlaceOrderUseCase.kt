package arch.example.trader.hexagon.application.usecase

import arch.example.trader.hexagon.domain.entity.Order
import arch.example.trader.hexagon.application.port.incoming.PlaceOrderPort
import arch.example.trader.hexagon.application.port.outgoing.OrderBook

class PlaceOrderUseCase(
    val orderBook: OrderBook
): PlaceOrderPort {

    override fun placeOrder(order: Order) {

        // validate order

        // run policies

        // save

    }
}