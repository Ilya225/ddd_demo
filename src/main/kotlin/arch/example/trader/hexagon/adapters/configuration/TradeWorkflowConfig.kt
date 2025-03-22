package arch.example.trader.hexagon.adapters.configuration

import arch.example.trader.hexagon.application.port.incoming.*
import arch.example.trader.hexagon.application.port.incoming.dto.PaymentRequest
import arch.example.trader.hexagon.application.tradeworkflow.stage.StageResult
import arch.example.trader.hexagon.application.tradeworkflow.stage.TradeWorkflowStage
import arch.example.trader.hexagon.application.tradeworkflow.stage.TradeWorkflowStageType
import arch.example.trader.hexagon.application.tradeworkflow.stage.TradeWorkflowStageType.*
import arch.example.trader.hexagon.domain.port.outgoing.LedgerPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TradeWorkflowConfig {

    @Bean
    fun transitions() =
        mapOf<TradeWorkflowStageType, Set<TradeWorkflowStageType>>(
            INITIATED to setOf(ORDER_PLACED),
            ORDER_PLACED to setOf(ORDER_MATCHED),
            ORDER_MATCHED to setOf(FEES_CALCULATED, DONE),
            FEES_CALCULATED to setOf(DISCOUNT_APPLIED),
            DISCOUNT_APPLIED to setOf(PAYMENT_EXECUTED),
            PAYMENT_EXECUTED to setOf(ASSETS_TRANSFERRED),
            ASSETS_TRANSFERRED to setOf(AUDITED),
            AUDITED to setOf(DONE),
        )

    @Bean
    fun tradeWorkflowSetup(
        placeOrderPort: PlaceOrderPort,
        matchOrderPort: MatchOrderPort,
        feesProviderPort: FeesProviderPort,
        applyDiscountPort: ApplyDiscountPort,
        paymentExecutionPort: PaymentExecutionPort,
        transferAssetsPort: TransferAssetsPort,
        ledgerPort: LedgerPort
    ) =
        mapOf<TradeWorkflowStageType, TradeWorkflowStage>(
            INITIATED to placeOrderStage(placeOrderPort),
            ORDER_PLACED to matchOrderStage(matchOrderPort),
            ORDER_MATCHED to feesCalculateStage(feesProviderPort),
            FEES_CALCULATED to applyDiscountStage(applyDiscountPort),
            DISCOUNT_APPLIED to paymentExecutionStage(paymentExecutionPort),
            PAYMENT_EXECUTED to transferAssetsStage(transferAssetsPort),
            ASSETS_TRANSFERRED to writeToLedgerStage(ledgerPort),
            AUDITED to TradeWorkflowStage { StageResult(DONE, it.copy(state = DONE)) },
        )


    fun placeOrderStage(placeOrderPort: PlaceOrderPort) =
        TradeWorkflowStage {
            if (it.validate()) {
                val order = placeOrderPort.placeOrder(it.newOrderRequest!!)
                StageResult(
                    ORDER_MATCHED,
                    it.copy(order = order, state = ORDER_PLACED)
                )
            }
            throw IllegalStateException("Order cannot be placed, workflow context is invalid: $it")
        }

    fun matchOrderStage(matchOrderPort: MatchOrderPort) =
        TradeWorkflowStage {
            if (it.validate()) {
                val deals = matchOrderPort.matchOrder(it.order!!)
                StageResult(
                    FEES_CALCULATED,
                    it.copy(deals = deals, state = ORDER_MATCHED)
                )
            }
            throw IllegalStateException("Order cannot be matched, workflow context is invalid: $it")
        }

    fun feesCalculateStage(feesProviderPort: FeesProviderPort) =
        TradeWorkflowStage {
            if (it.validate()) {
                val buyerFee = feesProviderPort.getBuyerFee(it.order!!)
                val sellerFee = feesProviderPort.getSellerFee(it.order)
                StageResult(
                    DISCOUNT_APPLIED,
                    it.copy(buyerFee = buyerFee, sellerFee = sellerFee, state = FEES_CALCULATED)
                )
            }
            throw IllegalStateException("Fees cannot be calculated, workflow context is invalid: $it")
        }

    fun applyDiscountStage(applyDiscountPort: ApplyDiscountPort) =
        TradeWorkflowStage {
            if (it.validate()) {
                val buyerDiscount = applyDiscountPort.applyBuyerDiscount(it.order!!)
                val sellerDiscount = applyDiscountPort.applySellerDiscount(it.order)
                StageResult(
                    PAYMENT_EXECUTED,
                    it.copy(
                        buyerDiscount = buyerDiscount,
                        sellerDiscount = sellerDiscount,
                        state = DISCOUNT_APPLIED
                    )
                )
            }
            throw IllegalStateException("Order cannot be matched, workflow context is invalid: $it")
        }

    fun paymentExecutionStage(paymentExecutionPort: PaymentExecutionPort) =
        TradeWorkflowStage {
            if (it.validate()) {
                val payments = paymentExecutionPort.processPayment(
                    PaymentRequest(
                        it.deals,
                        it.order!!,
                        it.buyerFee!!,
                        it.sellerFee!!,
                        it.buyerDiscount!!,
                        it.sellerDiscount!!
                    )
                )
                StageResult(
                    ASSETS_TRANSFERRED,
                    it.copy(payments = payments, state = PAYMENT_EXECUTED)
                )
            }
            throw IllegalStateException("Order cannot be matched, workflow context is invalid: $it")
        }

    fun transferAssetsStage(transferAssetsPort: TransferAssetsPort) =
        TradeWorkflowStage {
            if (it.validate()) {
                val transferredAssets = transferAssetsPort.transferAsset(
                    it.order!!, it.deals
                )
                StageResult(
                    AUDITED,
                    it.copy(transferredAssets = transferredAssets, state = ASSETS_TRANSFERRED)
                )
            }
            throw IllegalStateException("Order cannot be matched, workflow context is invalid: $it")
        }

    fun writeToLedgerStage(ledgerPort: LedgerPort) =
        TradeWorkflowStage {
            if (it.validate()) {
                val payments = ledgerPort.write(it.payments)
                StageResult(
                    DONE,
                    it.copy(payments = payments, state = AUDITED)
                )
            }
            throw IllegalStateException("Order cannot be matched, workflow context is invalid: $it")
        }
}
