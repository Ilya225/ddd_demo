package arch.example.trader.hexagon.domain.policy.order

import arch.example.trader.component.ddd.PredicatePolicy
import arch.example.trader.hexagon.domain.entity.Order
import arch.example.trader.hexagon.domain.entity.OrderType
import arch.example.trader.hexagon.domain.port.outgoing.WalletRepository

class UserHasSufficientFundsToBuyPolicy(
    private val walletRepository: WalletRepository
) : PredicatePolicy<Order> {

    override fun invoke(model: Order): Boolean =
        when (model.type) {
            OrderType.SELL -> true
            OrderType.BUY -> walletRepository.findByUserId(model.traderId)
                ?.enoughFunds(model.price, model.quantity) ?: false
        }
}