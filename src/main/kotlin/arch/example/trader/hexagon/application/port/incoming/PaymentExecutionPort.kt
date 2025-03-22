package arch.example.trader.hexagon.application.port.incoming

import arch.example.trader.hexagon.application.port.incoming.dto.PaymentRequest
import arch.example.trader.hexagon.domain.entity.Payment

interface PaymentExecutionPort {
    fun processPayment(request: PaymentRequest): List<Payment>
}
