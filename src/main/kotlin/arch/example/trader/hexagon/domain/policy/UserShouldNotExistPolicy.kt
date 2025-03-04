package arch.example.trader.hexagon.domain.policy

import arch.example.trader.component.ddd.Policy
import arch.example.trader.hexagon.domain.entity.User
import arch.example.trader.hexagon.domain.port.outgoing.UserRepository


class UserShouldNotExistPolicy(
    val userRepository: UserRepository
) : Policy<User, Boolean> {

    override fun invoke(model: User): Boolean =
        !userRepository.existsByEmailOrUsername(model.email, model.username)

}