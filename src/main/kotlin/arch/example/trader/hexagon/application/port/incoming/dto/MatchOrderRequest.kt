package arch.example.trader.hexagon.application.port.incoming.dto

import arch.example.trader.hexagon.domain.entity.Order

data class MatchOrderRequest(
    val order: Order
)