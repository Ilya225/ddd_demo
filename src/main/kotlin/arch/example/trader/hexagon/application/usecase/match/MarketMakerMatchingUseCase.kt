package arch.example.trader.hexagon.application.usecase.match

import arch.example.trader.hexagon.application.port.incoming.MatchOrderPort
import arch.example.trader.hexagon.domain.entity.Deal
import arch.example.trader.hexagon.domain.entity.DealId
import arch.example.trader.hexagon.domain.entity.Order
import arch.example.trader.hexagon.domain.port.outgoing.DealRepository
import arch.example.trader.hexagon.domain.port.outgoing.OrderBook
import java.time.Instant
import java.util.*

class MarketMakerMatchingUseCase(
    private val orderBook: OrderBook,
    private val dealRepository: DealRepository
) : MatchOrderPort {

    override fun matchOrder(newOrder: Order): List<Deal> {
        val makerOrders = orderBook.getMarketMakersOrdersBy(newOrder)
        val deals = mutableListOf<Deal>()
        var remainingQuantity = newOrder.quantity

        for (makerOrder in makerOrders) {

            if (remainingQuantity > 0) {
                val tradeQuantity = minOf(remainingQuantity, makerOrder.quantity)

                val deal = executeTrade(newOrder.copy(quantity = tradeQuantity), makerOrder)
                val allocatedOrder = makerOrder.allocate(tradeQuantity)
                orderBook.updateOrder(allocatedOrder)

                deals.add(deal)
                remainingQuantity -= tradeQuantity
            }
        }

        if (remainingQuantity > 0) {
            orderBook.placeOrder(newOrder.allocate(newOrder.quantity - remainingQuantity))
        }

        return deals
    }

    private fun executeTrade(buyOrder: Order, sellOrder: Order): Deal {
        val matchedQuantity = minOf(buyOrder.quantity, sellOrder.quantity)
        val sealedDeal = Deal(
            DealId(UUID.randomUUID()),
            sellOrder.id,
            sellOrder.traderId,
            buyOrder.id,
            buyOrder.traderId,
            buyOrder.assetId,
            matchedQuantity,
            sellOrder.price,
            sellOrder.unitPrice,
            Instant.now()
        )


        orderBook.cancelOrder(buyOrder)
        orderBook.cancelOrder(sellOrder)

        dealRepository.save(sealedDeal)

        return sealedDeal
    }
}