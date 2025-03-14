package arch.example.trader.cqrs.command.domain.policy

import arch.example.trader.component.ddd.PredicatePolicy
import arch.example.trader.cqrs.command.domain.entity.User
import arch.example.trader.cqrs.query.repository.ReadUserRepository
import org.springframework.stereotype.Service

@Service
class UserShouldNotExistPolicy(
    private val userRepository: ReadUserRepository
) : PredicatePolicy<User> {

    override fun invoke(model: User): Boolean =
        !userRepository.existsByEmailOrUsername(model.email, model.username)

}