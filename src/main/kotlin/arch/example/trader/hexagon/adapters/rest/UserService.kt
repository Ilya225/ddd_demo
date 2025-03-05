package arch.example.trader.hexagon.adapters.rest

import arch.example.trader.hexagon.adapters.rest.request.UserRequest
import arch.example.trader.hexagon.domain.entity.User
import arch.example.trader.hexagon.domain.entity.UserId
import arch.example.trader.hexagon.application.port.incoming.CreateUserAlwaysValidPort
import org.springframework.stereotype.Service
import org.springframework.util.IdGenerator
import java.time.Instant

@Service
class UserService(
    val idGenerator: IdGenerator,
    val createUserAlwaysValidPort: CreateUserAlwaysValidPort
) {

    fun createUser(userRequest: UserRequest): UserId {
        val user = User(
            UserId(idGenerator.generateId()),
            userRequest.username,
            userRequest.email,
            userRequest.password,
            userRequest.firstname,
            userRequest.lastname,
            Instant.now()
        )

        return createUserAlwaysValidPort.createUser(user)
    }
}
