package arch.example.trader.cqrs.command.handler

import arch.example.trader.component.cqrs.handler.CommandHandler
import arch.example.trader.cqrs.command.domain.entity.Order
import arch.example.trader.cqrs.command.domain.entity.OrderId
import arch.example.trader.cqrs.command.domain.entity.event.OrderAcceptedEvent
import arch.example.trader.cqrs.command.domain.policy.OrderPlacementPolicy
import arch.example.trader.cqrs.command.domain.repository.OrderBook
import arch.example.trader.cqrs.command.input.PlaceOrderCommand
import arch.example.trader.cqrs.command.output.PlaceOrderCommandAccepted
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.*

@Service
class PlaceOrderHandler(
    private val orderBook: OrderBook,
    private val orderPlacementPolicy: OrderPlacementPolicy,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : CommandHandler<PlaceOrderCommand, PlaceOrderCommandAccepted> {

    override fun handle(cmd: PlaceOrderCommand): PlaceOrderCommandAccepted {

        val orderId = OrderId(UUID.randomUUID())

        val newOrder = Order(
            orderId,
            cmd.traderId,
            cmd.assetId,
            cmd.type,
            cmd.price,
            cmd.quantity,
            cmd.placedAt
        )

        if (orderPlacementPolicy.invoke(newOrder)) {
            orderBook.placeOrder(newOrder)
            applicationEventPublisher.publishEvent(OrderAcceptedEvent(orderId))
            return PlaceOrderCommandAccepted(orderId)
        }

        throw RuntimeException("Failed to place order")
    }
}