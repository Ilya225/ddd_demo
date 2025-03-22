package arch.example.trader.hexagon.domain.port.outgoing

import arch.example.trader.hexagon.domain.entity.Payment

interface LedgerPort {
    fun write(payments: List<Payment>): List<Payment>
}
