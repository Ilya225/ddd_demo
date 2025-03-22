package arch.example.trader.cqrs.query.listener

import arch.example.trader.cqrs.command.domain.entity.event.OrderMatchedEvent
import arch.example.trader.cqrs.query.CalculateFeeQuery
import arch.example.trader.cqrs.query.listener.events.FeeCalculatedEvent
import arch.example.trader.cqrs.query.listener.events.OrderFeeCalculatedEvent
import arch.example.trader.cqrs.query.resolver.CalculateFeesForOrderResolver
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class FeeCalculatorStepListener(
    private val calculateFeesForOrderResolver: CalculateFeesForOrderResolver,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    @EventListener
    fun on(event: OrderMatchedEvent) {

        val fees = event.sealedDeals.map { sealedEvent ->
            val calculateQuery = CalculateFeeQuery(
                sealedEvent.dealId,
                sealedEvent.buyerId,
                sealedEvent.sellerId,
                sealedEvent.assetId,
                sealedEvent.quantity,
                sealedEvent.unitPrice
            )

            val orderFee = calculateFeesForOrderResolver.resolve(calculateQuery)

            FeeCalculatedEvent(
                sealedEvent.dealId,
                sealedEvent.sellerId,
                sealedEvent.buyerId,
                sealedEvent.assetId,
                orderFee.totalPrice,
                orderFee.buyerFee,
                orderFee.sellerFee
            )
        }.toSet()

        val orderFeesCalculatedEvent = OrderFeeCalculatedEvent(
            event.orderId, fees
        )

        applicationEventPublisher.publishEvent(orderFeesCalculatedEvent)
    }
}