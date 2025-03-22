package arch.example.trader.cqrs.query.repository

import arch.example.trader.component.ddd.vo.Ratio
import arch.example.trader.cqrs.command.domain.entity.AssetType
import arch.example.trader.cqrs.command.domain.entity.UserId

interface OrderFeeReadRepository {
    fun findBuyerFeeRatio(buyerId: UserId, assetType: AssetType): Ratio
    fun findSellerFeeRatio(sellerId: UserId, assetType: AssetType): Ratio
}