package arch.example.trader.layer.domain

import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class LedgerRecord(
    val transactionId: UUID,
    val timestamp: Instant,
    val assetId: UUID,
    val buyerId: UUID,
    val sellerId: UUID,
    val quantity: Long,
    val unitPrice: BigDecimal,
    val totalTradeAmount: BigDecimal,
    val buyerFee: BigDecimal,
    val sellerFee: BigDecimal,
    val finalBuyerAmount: BigDecimal,
    val finalSellerAmount: BigDecimal,
    val status: String,
    val dealId: UUID,
)