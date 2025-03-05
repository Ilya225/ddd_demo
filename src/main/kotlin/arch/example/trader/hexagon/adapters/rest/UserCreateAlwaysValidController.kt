package arch.example.trader.hexagon.adapters.rest

import arch.example.trader.hexagon.adapters.rest.request.UserRequest
import arch.example.trader.hexagon.domain.entity.UserId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/hexagon/users")
class UserCreateAlwaysValidController(
    val userService: UserService
) {

    @PostMapping
    fun create(@RequestBody userRequest:UserRequest):ResponseEntity<UserId> {
        return ResponseEntity
            .ok(
                userService
                    .createUser(userRequest)
            )
    }

}


