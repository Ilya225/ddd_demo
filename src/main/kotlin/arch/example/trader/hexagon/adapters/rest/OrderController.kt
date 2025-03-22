package arch.example.trader.hexagon.adapters.rest

import arch.example.trader.hexagon.application.port.incoming.RunTradeWorkflowPort
import arch.example.trader.hexagon.application.port.incoming.dto.NewOrderRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/hexagonal/orders")
class OrderController(
    private val runTradeWorkflowPort: RunTradeWorkflowPort
) {

    @PostMapping
    fun newOrder(@RequestBody newOrderRequest: NewOrderRequest) {
        runTradeWorkflowPort.runTradeWorkflow(newOrderRequest)
    }
}