package arch.example.trader.hexagon.application.port.incoming

import arch.example.trader.hexagon.domain.entity.Deal
import arch.example.trader.hexagon.domain.entity.Order

interface MatchOrderPort {
    fun matchOrder(order: Order): List<Deal>
}