package arch.example.trader.layer.service

import arch.example.trader.layer.domain.Deal
import java.math.BigDecimal
import java.util.*

class PaymentService(
    private val transactionFeeService: TransactionFeeService,
    private val traderDiscountService: TraderDiscountService,
    private val orderService: OrderService,
    private val walletService: WalletService,
    private val portfolioService: PortfolioService,
    private val ledgerService: LedgerService
) {
    fun applyFees(deals: List<Deal>) {
        for (deal in deals) {
            val buyerOrder = orderService.findOrder(deal.buyOrderId)
                ?: throw RuntimeException("Buyer order not found: ${deal.buyOrderId}")
            val sellerOrder = orderService.findOrder(deal.sellOrderId)
                ?: throw RuntimeException("Seller order not found: ${deal.sellOrderId}")

            val rawTakerFee = transactionFeeService.calculateTakerFee(deal)
            val rawMakerFee = transactionFeeService.calculateMakerFee(deal)

            val buyerId = buyerOrder.traderId
            val sellerId = sellerOrder.traderId

            val buyerDiscount = traderDiscountService.getBuyDiscount(buyerId)
            val sellerDiscount = traderDiscountService.getSellDiscount(sellerId)

            val buyerFee = rawTakerFee * (BigDecimal(1) - buyerDiscount)
            val sellerFee = rawMakerFee * (BigDecimal(1) - sellerDiscount)

            portfolioService.transferAssets(sellerId, buyerId, deal.assetId, deal.quantity)

            processPayment(deal, buyerId, sellerId, buyerFee, sellerFee)
        }
    }

    private fun processPayment(deal: Deal, buyer: UUID, seller: UUID, buyerFee: BigDecimal, sellerFee: BigDecimal) {
        val totalTradePrice = (deal.unitPrice * BigDecimal(deal.quantity))
        val totalBuyerCost = totalTradePrice + buyerFee
        val totalSellerRevenue = totalTradePrice - sellerFee

        walletService.debit(buyer, totalBuyerCost)
        walletService.credit(seller, totalSellerRevenue)

        ledgerService.writeToLedger(deal, buyerFee, sellerFee, totalBuyerCost, totalSellerRevenue, totalTradePrice)

    }

}
