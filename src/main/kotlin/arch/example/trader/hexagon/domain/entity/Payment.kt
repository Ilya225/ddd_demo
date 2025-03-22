package arch.example.trader.hexagon.domain.entity

import arch.example.trader.component.ddd.vo.Money
import java.util.UUID


@JvmInline
value class PaymentId(val id: UUID)

data class Payment(
    val id: PaymentId,
    val buyerOrderId: OrderId,
    val sellerOrderId: OrderId,
    val dealId: DealId,
    val assetId: AssetId,
    val buyerId: UserId,
    val sellerId: UserId,
    val quantity: Long,
    val unitPrice: Money,
    val totalTradeAmount: Money,
    val buyerFee: Money,
    val sellerFee: Money,
    val finalBuyerAmount: Money,
    val finalSellerAmount: Money,
)
