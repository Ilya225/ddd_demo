package arch.example.trader.hexagon.application.tradeworkflow

import arch.example.trader.hexagon.application.port.incoming.ApplyDiscountPort
import arch.example.trader.hexagon.application.port.incoming.FeesProviderPort
import arch.example.trader.hexagon.application.port.incoming.MatchOrderPort
import arch.example.trader.hexagon.application.port.incoming.PaymentExecutionPort
import arch.example.trader.hexagon.application.port.incoming.PlaceOrderPort
import arch.example.trader.hexagon.application.port.incoming.TransferAssetsPort
import arch.example.trader.hexagon.application.port.incoming.dto.NewOrderRequest
import arch.example.trader.hexagon.application.port.incoming.dto.PaymentRequest
import arch.example.trader.hexagon.domain.port.outgoing.LedgerPort

class TradeWorkflow(
    private val placeOrderPort: PlaceOrderPort,
    private val matchOrderPort: MatchOrderPort,
    private val feesProviderPort: FeesProviderPort,
    private val discountProviderPort: ApplyDiscountPort,
    private val paymentExecutionPort: PaymentExecutionPort,
    private val transferAssetsPort: TransferAssetsPort,
    private val ledgerPort: LedgerPort
) {

    fun run(newOrderRequest: NewOrderRequest) {

        val order = placeOrderPort.placeOrder(newOrderRequest)

        val deals = matchOrderPort.matchOrder(order)

        val buyerFee = feesProviderPort.getBuyerFee(order)
        val sellerFee = feesProviderPort.getSellerFee(order)

        val buyerAppliedDiscountRatio = discountProviderPort.applyBuyerDiscount(order)
        val sellerAppliedDiscountRatio = discountProviderPort.applySellerDiscount(order)

        val payments = paymentExecutionPort.processPayment(
            PaymentRequest(
                deals,
                order,
                buyerFee,
                sellerFee,
                buyerAppliedDiscountRatio,
                sellerAppliedDiscountRatio
            )
        )

        transferAssetsPort.transferAsset(order, deals)


        ledgerPort.write(payments)
    }
}