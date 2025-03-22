package arch.example.trader.cqrs.query.listener

import arch.example.trader.cqrs.query.GetDiscountQuery
import arch.example.trader.cqrs.query.listener.events.DiscountAppliedEvent
import arch.example.trader.cqrs.query.listener.events.OrderDiscountsAppliedEvent
import arch.example.trader.cqrs.query.listener.events.OrderFeeCalculatedEvent
import arch.example.trader.cqrs.query.resolver.OrderDiscountResolver
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class DiscountApplyStepListener(
    private val orderDiscountResolver: OrderDiscountResolver,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    @EventListener
    fun on(event: OrderFeeCalculatedEvent) {


        val appliedDiscounts = event.feesData.map { feeCalculatedEvent ->
            val query = GetDiscountQuery(
                feeCalculatedEvent.dealId,
                feeCalculatedEvent.buyerId,
                feeCalculatedEvent.sellerId,
                feeCalculatedEvent.assetId,
                feeCalculatedEvent.totalPrice,
                feeCalculatedEvent.buyerFee,
                feeCalculatedEvent.sellerFee
            )

            val discountView = orderDiscountResolver.resolve(query)

            DiscountAppliedEvent(
                feeCalculatedEvent.dealId,
                feeCalculatedEvent.buyerId,
                feeCalculatedEvent.sellerId,
                feeCalculatedEvent.assetId,
                discountView.buyerFeeAfterDiscount,
                discountView.sellerFeeAfterDiscount
            )
        }.toSet()

        val orderDiscountsAppliedEvent = OrderDiscountsAppliedEvent(
            event.orderId, appliedDiscounts
        )


        applicationEventPublisher.publishEvent(orderDiscountsAppliedEvent)
    }
}