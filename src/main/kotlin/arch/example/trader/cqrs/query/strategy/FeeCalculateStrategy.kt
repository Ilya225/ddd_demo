package arch.example.trader.cqrs.query.strategy

import arch.example.trader.component.ddd.Policy
import arch.example.trader.component.ddd.vo.Money
import arch.example.trader.cqrs.query.CalculateFeeQuery
import arch.example.trader.cqrs.query.repository.AssetReadRepository
import arch.example.trader.cqrs.query.repository.OrderFeeReadRepository
import arch.example.trader.cqrs.query.repository.UserReadRepository
import arch.example.trader.cqrs.query.view.OrderFeesView

class FeeCalculateStrategy(
    private val orderFeeReadRepository: OrderFeeReadRepository,
    private val assetReadRepository: AssetReadRepository,
    private val userReadRepository: UserReadRepository
) : Policy<CalculateFeeQuery, OrderFeesView> {

    override fun invoke(model: CalculateFeeQuery): OrderFeesView {
        val assetType = assetReadRepository.findAssetType(model.assetId)
            ?: throw RuntimeException("No Asset found ${model.assetId}")

        val buyerFeeRatio = orderFeeReadRepository.findBuyerFeeRatio(model.buyerId, assetType)
        val sellerFeeRatio = orderFeeReadRepository.findSellerFeeRatio(model.sellerId, assetType)

        val totalPrice = model.unitPrice * model.quantity

        val buyerFee = totalPrice * buyerFeeRatio
        val sellerFee = (totalPrice * sellerFeeRatio)


        return OrderFeeProjection(
            buyerFee, sellerFee, totalPrice,
        )
    }
}

private data class OrderFeeProjection(
    override val buyerFee: Money,
    override val sellerFee: Money,
    override val totalPrice: Money
) : OrderFeesView