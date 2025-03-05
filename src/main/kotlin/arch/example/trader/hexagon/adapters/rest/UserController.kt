package arch.example.trader.hexagon.adapters.rest

import arch.example.trader.hexagon.adapters.rest.request.UserRequest
import arch.example.trader.hexagon.domain.entity.UserId
import arch.example.trader.hexagon.application.port.incoming.CreateUserPort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/hexagon/users")
class UserController(
    val createUserPort: CreateUserPort
) {

    @PostMapping
    fun create(@RequestBody userRequest: UserRequest): ResponseEntity<UserId> {
        return ResponseEntity
            .ok(
                createUserPort
                    .createUser(
                        userRequest.username,
                        userRequest.email,
                        userRequest.password
                    )
            )
    }
}