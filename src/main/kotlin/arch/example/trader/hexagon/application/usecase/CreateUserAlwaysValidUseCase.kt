package arch.example.trader.hexagon.application.usecase

import arch.example.trader.hexagon.domain.policy.UserShouldNotExistPolicy
import arch.example.trader.hexagon.domain.entity.User
import arch.example.trader.hexagon.domain.entity.UserId
import arch.example.trader.hexagon.application.port.incoming.CreateUserAlwaysValidPort
import arch.example.trader.hexagon.application.port.outgoing.UserRepository
import org.springframework.stereotype.Service

@Service
class CreateUserAlwaysValidUseCase(
    val userRepository: UserRepository,
    val userShouldNotExistPolicy: UserShouldNotExistPolicy
) : CreateUserAlwaysValidPort {

    override fun createUser(user: User): UserId {

        if (!userShouldNotExistPolicy(user)) {
            throw IllegalArgumentException("Cannot create User. Provide unique email and username")
        }

        return userRepository.save(user)
    }
}