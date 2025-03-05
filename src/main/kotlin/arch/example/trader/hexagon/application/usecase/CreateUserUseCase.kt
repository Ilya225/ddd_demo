package arch.example.trader.hexagon.application.usecase

import arch.example.trader.hexagon.domain.policy.UserShouldNotExistPolicy
import arch.example.trader.hexagon.domain.entity.User
import arch.example.trader.hexagon.domain.entity.UserId
import arch.example.trader.hexagon.application.port.incoming.CreateUserPort
import arch.example.trader.hexagon.application.port.outgoing.UserRepository
import arch.example.trader.hexagon.domain.specification.UsersInValidStateSpecification
import org.springframework.stereotype.Service
import org.springframework.util.IdGenerator

@Service
class CreateUserUseCase(
    val userRepository: UserRepository,
    val idGenerator: IdGenerator,
    val userShouldNotExistPolicy: UserShouldNotExistPolicy
) : CreateUserPort {
    override fun createUser(username: String, email: String, password: String): UserId {

        val user = User(
            UserId(idGenerator.generateId()),
            username,
            email,
            password
        )

        val spec = UsersInValidStateSpecification()

        if (!spec.isSatisfiedBy(user)) {
            throw IllegalArgumentException("Cannot create User. The data provided is invalid")
        }

        if (!userShouldNotExistPolicy(user)) {
            throw IllegalArgumentException("Cannot create User. Provide unique email and username")
        }


        return userRepository.save(user)
    }
}