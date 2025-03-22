package arch.example.trader.hexagon.application.tradeworkflow.stage

import arch.example.trader.component.ddd.Specification
import arch.example.trader.component.ddd.and
import arch.example.trader.component.ddd.vo.Money
import arch.example.trader.component.ddd.vo.Ratio
import arch.example.trader.hexagon.application.port.incoming.dto.NewOrderRequest
import arch.example.trader.hexagon.domain.entity.AssetTransfer
import arch.example.trader.hexagon.domain.entity.Deal
import arch.example.trader.hexagon.domain.entity.Order
import arch.example.trader.hexagon.domain.entity.Payment


class TradeWorkflowRunner(
    private val stages: Map<TradeWorkflowStageType, TradeWorkflowStage>,
    private val transitions: Map<TradeWorkflowStageType, Set<TradeWorkflowStageType>>
) {

    fun run(newOrderRequest: NewOrderRequest) {

        val ctx = TradeWorkflowContext(
            state = TradeWorkflowStageType.INITIATED,
            newOrderRequest
        )

        var result = stages[ctx.state]!!.invoke(ctx)

        while (result.nextState != TradeWorkflowStageType.DONE) {
            if (transitions[result.context.state]!!.contains(result.nextState))
                result = stages[result.nextState]!!.invoke(result.context)
        }

    }
}

fun interface TradeWorkflowStage {
    fun invoke(input: TradeWorkflowContext): StageResult
}

data class StageResult(
    val nextState: TradeWorkflowStageType,
    val context: TradeWorkflowContext,
)

data class TradeWorkflowContext(
    val state: TradeWorkflowStageType,
    val newOrderRequest: NewOrderRequest?,
    val order: Order? = null,
    val deals: List<Deal> = listOf(),
    val buyerFee: Money? = null,
    val sellerFee: Money? = null,
    val buyerDiscount: Ratio? = null,
    val sellerDiscount: Ratio? = null,
    val payments: List<Payment> = listOf(),
    val transferredAssets: List<AssetTransfer> = listOf()

) {
    fun validate(): Boolean =
        this.state.validationRules.isSatisfiedBy(this)

}

enum class TradeWorkflowStageType(val validationRules: Specification<TradeWorkflowContext>) {
    INITIATED(hasNewOrderRequest),
    ORDER_PLACED(hasNewOrderRequest and hasOrder),
    ORDER_MATCHED(hasOrder and hasAtLeastOneDeal),
    FEES_CALCULATED(hasOrder and hasAtLeastOneDeal and hasBuyerFee and hasSellerFee),
    DISCOUNT_APPLIED(hasOrder and hasAtLeastOneDeal and hasBuyerFee and hasSellerFee and hasBuyerDiscount and hasSellerDiscount),
    PAYMENT_EXECUTED(hasAtLeastOnePayment),
    ASSETS_TRANSFERRED(hasAtLeastOneTransferredAsset),
    AUDITED(alwaysValid),
    DONE(alwaysValid)
}