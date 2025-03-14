package arch.example.trader.cqrs.command.handler

import arch.example.trader.component.cqrs.handler.CommandHandler
import arch.example.trader.cqrs.command.domain.entity.event.DealSealedEvent
import arch.example.trader.cqrs.command.domain.repository.OrderBook
import arch.example.trader.cqrs.command.input.MatchOrderCommand
import arch.example.trader.cqrs.command.output.OrderMatched
import arch.example.trader.cqrs.command.strategy.TradingStrategyFactory
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

        deals.forEach { deal ->
            applicationEventPublisher.publishEvent(DealSealedEvent(deal.id))
        }

        return OrderMatched(deals.map { it.id })
    }
}