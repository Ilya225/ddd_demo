package arch.example.trader.layer.controller

import arch.example.trader.layer.dto.OrderDto
import arch.example.trader.layer.service.OrderService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/layer/orders")
class OrderController(
    private val orderService: OrderService
) {

    @PostMapping
    fun placeOrder(orderDto: OrderDto) {
        orderService.placeOrder(orderDto)
    }
}