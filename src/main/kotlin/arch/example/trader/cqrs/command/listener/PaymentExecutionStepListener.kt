package arch.example.trader.cqrs.command.listener

import arch.example.trader.cqrs.query.listener.events.OrderDiscountsAppliedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class PaymentExecutionStepListener {


    @EventListener
    fun on(event: OrderDiscountsAppliedEvent) {

    }
}