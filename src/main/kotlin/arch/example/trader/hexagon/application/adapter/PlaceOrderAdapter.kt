package arch.example.trader.hexagon.application.adapter

import arch.example.trader.hexagon.domain.entity.Order
import arch.example.trader.hexagon.domain.port.incoming.PlaceOrderPort
import arch.example.trader.hexagon.domain.port.outgoing.OrderBook

class PlaceOrderAdapter(
    val orderBook: OrderBook
): PlaceOrderPort {

    override fun placeOrder(order: Order) {

        // validate order

        // run policies

        // save

    }
}