package arch.example.trader.layer.service

import arch.example.trader.layer.domain.Deal
import arch.example.trader.layer.domain.Order
import arch.example.trader.layer.domain.OrderType

class OrderMatchingService(
    private val orderBook: OrderBook,
    private val dealService: DealService,
    private val paymentService: PaymentService,
) {

    fun matchOrder(newOrder: Order) {
        val orders = if (newOrder.type == OrderType.BUY) orderBook.getAllSellOrders(newOrder.assetId)
        else orderBook.getAllBuyOrders(newOrder.assetId)
        val deals = mutableListOf<Deal>()

        var remainingQuantity = newOrder.quantity

        for (order in orders) {
            if (remainingQuantity > 0) {
                val tradeQuantity = minOf(remainingQuantity, order.quantity)

                val deal = dealService.makeADeal(newOrder.copy(quantity = tradeQuantity), order)


                orderBook.removeOrder(order)

                if (tradeQuantity < order.quantity) {
                    orderBook.addOrder(order.copy(quantity = order.quantity - tradeQuantity))
                }

                deals.add(deal)
                remainingQuantity -= tradeQuantity
            }
        }

        paymentService.applyFees(deals.toList())
    }

    private fun isMatch(order: Order, match: Order): Boolean {
        return if (order.type == OrderType.BUY) order.unitPrice >= match.unitPrice
        else order.unitPrice <= match.unitPrice
    }
}
