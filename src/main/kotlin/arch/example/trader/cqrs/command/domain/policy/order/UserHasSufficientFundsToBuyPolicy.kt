package arch.example.trader.cqrs.command.domain.policy.order

import arch.example.trader.component.ddd.PredicatePolicy
import arch.example.trader.cqrs.command.domain.entity.Order
import arch.example.trader.cqrs.command.domain.repository.WalletRepository

class UserHasSufficientFundsToBuyPolicy(
    private val walletRepository: WalletRepository
) : PredicatePolicy<Order> {

    override fun invoke(model: Order): Boolean {
        return walletRepository.findByUserId(model.traderId)?.enoughFunds(model.unitPrice, model.quantity) ?: false
    }

}