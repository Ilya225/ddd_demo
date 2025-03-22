package arch.example.trader.layer.service

import arch.example.trader.layer.domain.Deal
import arch.example.trader.layer.domain.LedgerRecord
import arch.example.trader.layer.repository.Ledger
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

class LedgerService(
    private val ledger: Ledger
) {

    fun writeToLedger(
        deal: Deal,
        buyerFee: BigDecimal,
        sellerFee: BigDecimal,
        totalBuyerCost: BigDecimal,
        totalSellerRevenue: BigDecimal,
        totalTradePrice: BigDecimal
    ) {
        val record = LedgerRecord(
            UUID.randomUUID(),
            timestamp = Instant.now(),
            assetId = deal.assetId,
            buyerId = deal.buyOrderId,
            sellerId = deal.sellOrderId,
            quantity = deal.quantity,
            unitPrice = deal.unitPrice,
            totalTradeAmount = totalTradePrice,
            buyerFee = buyerFee,
            sellerFee = sellerFee,
            finalBuyerAmount = totalBuyerCost,
            finalSellerAmount = totalSellerRevenue,
            status = "Completed",
            dealId = deal.id
        )

        ledger.save(record)
    }
}