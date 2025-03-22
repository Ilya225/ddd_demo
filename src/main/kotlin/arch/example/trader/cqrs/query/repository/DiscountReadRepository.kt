package arch.example.trader.cqrs.query.repository

import arch.example.trader.component.ddd.vo.Ratio
import arch.example.trader.cqrs.command.domain.entity.UserId

interface DiscountReadRepository {

    fun findBuyerDiscountRatio(buyerId: UserId): Ratio?

    fun findSellerDiscountRatio(sellerId: UserId): Ratio?
}