package arch.example.trader.cqrs.command.domain.policy.matching

import arch.example.trader.component.ddd.vo.Money
import arch.example.trader.cqrs.command.domain.entity.Deal
import arch.example.trader.cqrs.command.domain.entity.DealId
import arch.example.trader.cqrs.command.domain.entity.MatcherType
import arch.example.trader.cqrs.command.domain.entity.Order
import arch.example.trader.cqrs.command.domain.repository.DealRepository
import arch.example.trader.cqrs.command.domain.repository.OrderBook
import java.time.Instant
import java.util.*

class MarketMakerMatchingStrategy(
    private val orderBook: OrderBook,
    private val dealRepository: DealRepository
) : OrderMatchingStrategy {

    override fun invoke(newOrder: Order): List<Deal> {
        val makerOrders = orderBook.getPassiveOrders()
        val deals = mutableListOf<Deal>()
        var remainingQuantity = newOrder.quantity

        for (makerOrder in makerOrders) {

            if (remainingQuantity > 0) {
                val tradeQuantity = minOf(remainingQuantity, makerOrder.quantity)

                val deal = executeTrade(newOrder.copy(quantity = tradeQuantity), makerOrder)

                deals.add(deal)
                remainingQuantity -= tradeQuantity
            }
        }

        orderBook.placeOrder(newOrder)
        return listOf()
    }

    private fun executeTrade(buyOrder: Order, sellOrder: Order): Deal {
        val matchedQuantity = minOf(buyOrder.quantity, sellOrder.quantity)

        val sealedDeal = Deal(
            DealId(UUID.randomUUID()), sellOrder.id, buyOrder.id, buyOrder.traderId,
            sellOrder.traderId, buyOrder.assetId,
            matchedQuantity, sellOrder.unitPrice,
            Instant.now(), MatcherType.MARKET_MAKER
        )

        orderBook.cancelOrder(buyOrder)
        orderBook.cancelOrder(sellOrder)

        dealRepository.save(sealedDeal)

        return sealedDeal
    }
}