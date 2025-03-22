package arch.example.trader.hexagon.application.port.incoming.dto

import arch.example.trader.component.ddd.vo.Money
import arch.example.trader.component.ddd.vo.Ratio
import arch.example.trader.hexagon.domain.entity.Deal
import arch.example.trader.hexagon.domain.entity.Order

data class PaymentRequest(
    val deals: List<Deal>,
    val order: Order,
    val buyerFee: Money,
    val sellerFee: Money,
    val buyerDiscount: Ratio,
    val sellerDiscount: Ratio
)