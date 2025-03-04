package arch.example.trader.cqrs.application.command.domain.entity

import arch.example.trader.component.ddd.Specification
import arch.example.trader.component.ddd.and
import java.time.Instant
import java.util.UUID

@JvmInline
value class UserId(val id: UUID)

data class User(
    val id: UserId,
    val username: String,
    val email: String,
    val password: String,
    val firstname: String = "",
    val lastname: String = "",
    val createdAt: Instant = Instant.now()
) {

    init {
        if ((validEmail and validUsername).isSatisfiedBy(this)) {
            throw IllegalStateException("Cannot instantiate User with invalid email or username")
        }
    }

    companion object {
        val validUsername = Specification<User> { user -> user.username.length >= 8 &&  user.username.length < 64 }
        val validEmail = Specification<User> { user -> user.email.contains("@") && user.email.length > 5 }
    }
}



