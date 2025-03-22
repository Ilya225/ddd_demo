package arch.example.trader.hexagon.application.port.incoming

import arch.example.trader.hexagon.application.port.incoming.dto.NewOrderRequest

interface RunTradeWorkflowPort {

    fun runTradeWorkflow(newOrderRequest: NewOrderRequest)
}