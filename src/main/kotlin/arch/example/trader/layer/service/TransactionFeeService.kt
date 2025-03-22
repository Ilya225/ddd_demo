package arch.example.trader.layer.service

import arch.example.trader.layer.config.TradeFlowConfig
import arch.example.trader.layer.domain.Deal
import java.math.BigDecimal

class TransactionFeeService(
    private val tradeFlowConfig: TradeFlowConfig,
) {
    fun calculateTakerFee(deal: Deal): BigDecimal {
        return tradeFlowConfig.buyFee
    }

    fun calculateMakerFee(deal: Deal): BigDecimal {
        return tradeFlowConfig.sellFee
    }
}
