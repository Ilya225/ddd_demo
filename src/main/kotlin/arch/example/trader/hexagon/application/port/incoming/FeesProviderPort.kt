package arch.example.trader.hexagon.application.port.incoming

import arch.example.trader.component.ddd.vo.Money
import arch.example.trader.hexagon.domain.entity.Order

interface FeesProviderPort {
    fun getBuyerFee(order: Order): Money
    fun getSellerFee(order: Order): Money
}