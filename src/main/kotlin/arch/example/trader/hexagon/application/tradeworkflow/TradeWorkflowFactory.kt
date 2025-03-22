package arch.example.trader.hexagon.application.tradeworkflow

import arch.example.trader.hexagon.application.port.incoming.ApplyDiscountPort
import arch.example.trader.hexagon.application.port.incoming.FeesProviderPort
import arch.example.trader.hexagon.application.port.incoming.MatchOrderPort
import arch.example.trader.hexagon.application.port.incoming.PaymentExecutionPort
import arch.example.trader.hexagon.application.port.incoming.PlaceOrderPort
import arch.example.trader.hexagon.application.port.incoming.TransferAssetsPort
import arch.example.trader.hexagon.application.port.incoming.dto.NewOrderRequest
import arch.example.trader.hexagon.domain.entity.AssetType
import arch.example.trader.hexagon.domain.port.outgoing.LedgerPort

class TradeWorkflowFactory(
    private val matchers: Map<AssetType, MatchOrderPort>,
    private val placeOrderPort: PlaceOrderPort,
    private val feesProviderPort: FeesProviderPort,
    private val applyDiscountPort: ApplyDiscountPort,
    private val transferAssetsPort: TransferAssetsPort,
    private val paymentProviders: List<PaymentExecutionPort>,
    private val ledger: LedgerPort
) {


    fun createFlow(newOrder: NewOrderRequest): TradeWorkflow {
        val matcher = findProperMatcher(newOrder)
        val payment = findProperPaymentProvider(newOrder)

        return TradeWorkflow(
            placeOrderPort,
            matcher,
            feesProviderPort,
            applyDiscountPort,
            payment,
            transferAssetsPort,
            ledger
        )
    }

    private fun findProperPaymentProvider(request: NewOrderRequest): PaymentExecutionPort {
        TODO()
    }

    private fun findProperMatcher(request: NewOrderRequest): MatchOrderPort {
        TODO()
    }
}
