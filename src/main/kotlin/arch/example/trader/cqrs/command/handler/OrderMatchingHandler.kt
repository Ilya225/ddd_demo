package arch.example.trader.cqrs.command.handler

import arch.example.trader.component.cqrs.handler.CommandHandler
import arch.example.trader.cqrs.command.domain.entity.event.DealSealedEvent
import arch.example.trader.cqrs.command.domain.entity.event.OrderMatchedEvent
import arch.example.trader.cqrs.command.domain.repository.OrderBook
import arch.example.trader.cqrs.command.factory.TradingStrategyFactory
import arch.example.trader.cqrs.command.input.MatchOrderCommand
import arch.example.trader.cqrs.command.output.OrderMatched
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class OrderMatchingHandler(
    private val orderBook: OrderBook,
    private val tradingStrategyFactory: TradingStrategyFactory,
    private val applicationEventPublisher: ApplicationEventPublisher
) : CommandHandler<MatchOrderCommand, OrderMatched> {

    override fun handle(cmd: MatchOrderCommand): OrderMatched {
        val order = orderBook.findOrder(cmd.id) ?: throw RuntimeException("No order in the orderBook: ${cmd.id}")

        val deals = tradingStrategyFactory.obtainStrategy(order)
            .invoke(order)

        val sealedDealEvents = deals.map { deal ->
            DealSealedEvent(
                deal.id,
                deal.sellOrderId, deal.buyOrderId, deal.buyerId, deal.sellerId,
                deal.assetId, deal.quantity, deal.unitPrice, deal.matchedBy
            )
        }.toSet()

        val orderMatchedEvent = OrderMatchedEvent(
            order.id,
            sealedDealEvents
        )

        applicationEventPublisher.publishEvent(orderMatchedEvent)

        return OrderMatched(order.id, deals.map { it.id })
    }
}