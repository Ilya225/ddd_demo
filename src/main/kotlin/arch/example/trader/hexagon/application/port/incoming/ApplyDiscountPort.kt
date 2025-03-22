package arch.example.trader.hexagon.application.port.incoming

import arch.example.trader.component.ddd.vo.Ratio
import arch.example.trader.hexagon.domain.entity.Order

interface ApplyDiscountPort {
    fun applyBuyerDiscount(order: Order): Ratio
    fun applySellerDiscount(order: Order): Ratio
}
