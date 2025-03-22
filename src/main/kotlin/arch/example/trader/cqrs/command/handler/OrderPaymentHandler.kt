package arch.example.trader.cqrs.command.handler

import arch.example.trader.component.cqrs.handler.CommandHandler
import arch.example.trader.cqrs.command.domain.repository.PaymentRepository
import arch.example.trader.cqrs.command.input.OrderPaymentCommand
import arch.example.trader.cqrs.command.output.OrderPaymentAccepted
import org.springframework.stereotype.Service

@Service
class OrderPaymentHandler(
    private val paymentRepository: PaymentRepository
) : CommandHandler<OrderPaymentCommand, OrderPaymentAccepted> {


    override fun handle(cmd: OrderPaymentCommand): OrderPaymentAccepted {
        TODO("Not yet implemented")
    }
}