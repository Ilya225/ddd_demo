package arch.example.trader.hexagon.application.usecase.payment

import arch.example.trader.component.ddd.vo.Money
import arch.example.trader.hexagon.application.port.incoming.PaymentExecutionPort
import arch.example.trader.hexagon.application.port.incoming.dto.PaymentRequest
import arch.example.trader.hexagon.domain.entity.Deal
import arch.example.trader.hexagon.domain.entity.Payment
import arch.example.trader.hexagon.domain.entity.PaymentId
import arch.example.trader.hexagon.domain.entity.UserId
import arch.example.trader.hexagon.domain.port.outgoing.WalletRepository
import java.util.*

class MainPaymentExecutionUseCase(
    private val walletRepository: WalletRepository,
) : PaymentExecutionPort {

    override fun processPayment(request: PaymentRequest): List<Payment> {
        val deals = request.deals

        return deals.map { deal ->

            val buyerId = deal.buyerId
            val sellerId = deal.sellerId

            val buyerFee = request.buyerFee - (request.buyerFee * request.buyerDiscount)
            val sellerFee = request.sellerFee - (request.sellerFee * request.sellerDiscount)

            processPayment(deal, buyerId, sellerId, buyerFee, sellerFee)
        }
    }

    private fun processPayment(deal: Deal, buyer: UserId, seller: UserId, buyerFee: Money, sellerFee: Money): Payment {
        val totalTradePrice = deal.price
        val totalBuyerCost = totalTradePrice + buyerFee
        val totalSellerRevenue = totalTradePrice - sellerFee

        val buyerWallet =
            walletRepository.findByUserId(buyer) ?: throw IllegalStateException("Buyer has no wallet: $buyer")
        val sellerWallet =
            walletRepository.findByUserId(seller) ?: throw IllegalStateException("Seller has no wallet: $buyer")
        val buyerUpdatedWallet = buyerWallet.withDraw(totalBuyerCost)
        val sellerUpdatedWallet = sellerWallet.deposit(totalSellerRevenue)

        walletRepository.save(buyerUpdatedWallet)
        walletRepository.save(sellerUpdatedWallet)

        return Payment(
            PaymentId(UUID.randomUUID()),
            deal.buyOrderId,
            deal.sellOrderId,
            deal.dealId,
            deal.assetId,
            deal.buyerId,
            deal.sellerId,
            deal.quantity,
            deal.unitPrice,
            totalTradePrice,
            buyerFee,
            sellerFee,
            totalBuyerCost,
            totalSellerRevenue
        )
    }
}