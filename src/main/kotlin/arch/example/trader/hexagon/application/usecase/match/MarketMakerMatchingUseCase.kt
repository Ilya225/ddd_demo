package arch.example.trader.hexagon.application.usecase.match

import arch.example.trader.hexagon.application.port.incoming.MatchOrderPort
import arch.example.trader.hexagon.domain.entity.Deal
import arch.example.trader.hexagon.domain.entity.DealId
import arch.example.trader.hexagon.domain.entity.Order
import arch.example.trader.hexagon.domain.port.outgoing.DealRepository
import arch.example.trader.hexagon.domain.port.outgoing.MarketMakersBook
import arch.example.trader.hexagon.domain.port.outgoing.OrderBook
import java.time.Instant
import java.util.UUID

class MarketMakerMatchingUseCase(
    private val orderBook: OrderBook,
    private val marketMakersBook: MarketMakersBook,
    private val dealRepository: DealRepository
) : MatchOrderPort {

    override fun matchOrder(newOrder: Order): List<Deal> {
        val makerOrders = marketMakersBook.getPassiveOrders()
        if (makerOrders.isNotEmpty()) {
            val deal = executeTrade(newOrder, makerOrders.first())

            applyTakerFee(newOrder)

            return listOf(deal)
        }

        orderBook.placeOrder(newOrder)
        return listOf()
    }

    private fun applyTakerFee(order: Order) {

    }

    private fun executeTrade(buyOrder: Order, sellOrder: Order): Deal {
        val matchedQuantity = minOf(buyOrder.quantity, sellOrder.quantity)
        val sealedDeal = Deal(
            DealId(UUID.randomUUID()), sellOrder.id, buyOrder.id, buyOrder.assetId,
            matchedQuantity, sellOrder.price, Instant.now()
        )


        orderBook.cancelOrder(buyOrder)
        orderBook.cancelOrder(sellOrder)

        dealRepository.save(sealedDeal)

        return sealedDeal
    }
}