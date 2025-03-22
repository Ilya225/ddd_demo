package arch.example.trader.layer.service

import arch.example.trader.layer.domain.OrderType
import arch.example.trader.layer.repository.DiscountRepository
import java.math.BigDecimal
import java.util.*

class TraderDiscountService(
    private val discountRepository: DiscountRepository
) {

    fun getBuyDiscount(traderId: UUID): BigDecimal {
        return discountRepository.findByUserIdAndOrderType(traderId, OrderType.BUY)?.discountRatio ?: BigDecimal(0.0)
    }

    fun getSellDiscount(traderId: UUID): BigDecimal {
        return discountRepository.findByUserIdAndOrderType(traderId, OrderType.SELL)?.discountRatio ?: BigDecimal(0.0)
    }
}
