package arch.example.trader.cqrs.query.strategy

import arch.example.trader.component.ddd.Policy
import arch.example.trader.component.ddd.vo.Money
import arch.example.trader.component.ddd.vo.Ratio
import arch.example.trader.cqrs.query.GetDiscountQuery
import arch.example.trader.cqrs.query.repository.DiscountReadRepository
import arch.example.trader.cqrs.query.view.CalculatedDiscountView
import java.math.BigDecimal

class DiscountCalculateStrategy(
    private val discountReadRepository: DiscountReadRepository
) : Policy<GetDiscountQuery, CalculatedDiscountView> {

    override fun invoke(model: GetDiscountQuery): CalculatedDiscountView {
        val buyerDiscountRatio = discountReadRepository.findBuyerDiscountRatio(model.buyerId) ?: Ratio(BigDecimal(0))
        val sellerDiscountRatio = discountReadRepository.findSellerDiscountRatio(model.sellerId) ?: Ratio(BigDecimal(0))

        val buyerFeeAfterDiscount = model.buyerFee - (model.buyerFee * buyerDiscountRatio)
        val sellerFeeAfterDiscount = model.sellerFee - (model.sellerFee * sellerDiscountRatio)

        return CalculatedDiscountProjection(
            buyerFeeAfterDiscount,
            sellerFeeAfterDiscount
        )
    }
}


private data class CalculatedDiscountProjection(
    override val buyerFeeAfterDiscount: Money,
    override val sellerFeeAfterDiscount: Money
) : CalculatedDiscountView