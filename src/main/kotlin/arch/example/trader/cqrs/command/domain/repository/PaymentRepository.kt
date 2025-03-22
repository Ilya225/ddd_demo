package arch.example.trader.cqrs.command.domain.repository

import arch.example.trader.cqrs.command.domain.entity.Payment

interface PaymentRepository {
    fun save(payment: Payment): Payment
}