package arch.example.trader.cqrs.command.domain.policy

import arch.example.trader.component.ddd.PredicatePolicy
import arch.example.trader.cqrs.command.domain.entity.User
import arch.example.trader.cqrs.query.repository.UserReadRepository
import org.springframework.stereotype.Service

@Service
class UserShouldNotExistPolicy(
    private val userRepository: UserReadRepository
) : PredicatePolicy<User> {

    override fun invoke(model: User): Boolean =
        !userRepository.existsByEmailOrUsername(model.email, model.username)

}