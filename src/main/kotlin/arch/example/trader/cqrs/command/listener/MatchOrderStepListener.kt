package arch.example.trader.cqrs.command.listener

import arch.example.trader.cqrs.command.domain.entity.event.OrderAcceptedEvent
import arch.example.trader.cqrs.command.handler.OrderMatchingHandler
import arch.example.trader.cqrs.command.input.MatchOrderCommand
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class MatchOrderStepListener(
    private val orderMatchingHandler: OrderMatchingHandler
) {

    @EventListener
    fun on(event: OrderAcceptedEvent) {
        orderMatchingHandler.handle(MatchOrderCommand(event.orderId))
    }
}