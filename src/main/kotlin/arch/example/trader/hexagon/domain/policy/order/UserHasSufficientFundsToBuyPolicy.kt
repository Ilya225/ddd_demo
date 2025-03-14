package arch.example.trader.hexagon.domain.policy.order

import arch.example.trader.component.ddd.PredicatePolicy
import arch.example.trader.hexagon.domain.entity.Order
import arch.example.trader.hexagon.domain.port.outgoing.WalletRepository

class UserHasSufficientFundsToBuyPolicy(
    private val walletRepository: WalletRepository
) : PredicatePolicy<Order> {

    override fun invoke(model: Order): Boolean {
        return walletRepository.findByUserId(model.traderId)?.enoughFunds(model.price, model.quantity) ?: false
    }

}