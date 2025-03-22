package arch.example.trader.cqrs.infrastructure.repository

import arch.example.trader.component.ddd.vo.Ratio
import arch.example.trader.cqrs.command.domain.entity.AssetType
import arch.example.trader.cqrs.command.domain.entity.UserId
import arch.example.trader.cqrs.query.repository.OrderFeeReadRepository
import java.math.BigDecimal

class OrderFeeReadRepoInMemory : OrderFeeReadRepository {

    private fun assetFee(assetType: AssetType): Ratio {
        return when(assetType) {
            AssetType.STOCK, AssetType.COMMODITY -> Ratio(BigDecimal(0.01))
            AssetType.OPTION, AssetType.FUTURE, AssetType.FOREX -> Ratio(BigDecimal(0.005))
            AssetType.CRYPTO -> Ratio(BigDecimal(0.02))
        }
    }

    override fun findBuyerFeeRatio(
        buyerId: UserId,
        assetType: AssetType
    ): Ratio {
        return assetFee(assetType)
    }

    override fun findSellerFeeRatio(
        sellerId: UserId,
        assetType: AssetType
    ): Ratio {
        return assetFee(assetType)
    }
}