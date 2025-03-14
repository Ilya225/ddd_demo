package arch.example.trader.cqrs.api.rest

import arch.example.trader.cqrs.command.input.CreateUserCommand
import arch.example.trader.cqrs.command.handler.CreateUserHandler
import arch.example.trader.cqrs.command.domain.entity.UserId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/cqrs/users")
class UserController(
    val createUserHandler: CreateUserHandler
) {

    @PostMapping
    fun create(@RequestBody command: CreateUserCommand): ResponseEntity<UserId> {
        return ResponseEntity
            .ok(
                createUserHandler.handle(command)
            )
    }
}