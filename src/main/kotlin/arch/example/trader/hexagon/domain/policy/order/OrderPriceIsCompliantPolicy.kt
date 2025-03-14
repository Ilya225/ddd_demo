package arch.example.trader.hexagon.domain.policy.order

import arch.example.trader.component.ddd.PredicatePolicy
import arch.example.trader.hexagon.domain.entity.AssetId
import arch.example.trader.hexagon.domain.entity.Order
import arch.example.trader.component.ddd.vo.Money
import arch.example.trader.hexagon.domain.port.outgoing.DealRepository
import arch.example.trader.hexagon.domain.port.outgoing.IPORepository
import java.math.BigDecimal

class OrderPriceIsCompliantPolicy(
    private val dealRepository: DealRepository,
    private val ipoRepository: IPORepository
) : PredicatePolicy<Order> {

    override fun invoke(model: Order): Boolean {
        val marketPrice = getMarketPrice(model.assetId)
        return priceIsValid(model, marketPrice)
    }

    fun priceIsValid(model: Order, marketPrice: Money): Boolean {
        val priceLimit = 0.10
        val minPrice = marketPrice.amount.multiply(BigDecimal(1 - priceLimit))
        val maxPrice = marketPrice.amount.multiply(BigDecimal(1 + priceLimit))

        return model.unitPrice.amount in minPrice..maxPrice
    }

    fun isDecreasingOrder(model: Order, marketPrice: BigDecimal): Boolean {
        val dropPercentage = (marketPrice - model.unitPrice.amount) / marketPrice
        return dropPercentage >= BigDecimal(0.07)  // 7% limit
    }


    private fun getMarketPrice(assetId: AssetId): Money {
        return dealRepository.findByAssetIdOrderByCreatedAtDesc(assetId)?.price
            ?: ipoRepository.findOneByTradableId(assetId)?.price
            ?: throw IllegalStateException("No price for the asset: $assetId")
    }
}