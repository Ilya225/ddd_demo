package arch.example.trader.hexagon.application.usecase

import arch.example.trader.hexagon.application.tradeworkflow.TradeWorkflowFactory
import arch.example.trader.hexagon.application.port.incoming.RunTradeWorkflowPort
import arch.example.trader.hexagon.application.port.incoming.dto.NewOrderRequest

class RunTradeWorkflowUseCase(
    private val tradeWorkflowFactory: TradeWorkflowFactory
): RunTradeWorkflowPort {


    override fun runTradeWorkflow(newOrderRequest: NewOrderRequest) {
        val workflow = tradeWorkflowFactory.createFlow(newOrderRequest)
    }
}