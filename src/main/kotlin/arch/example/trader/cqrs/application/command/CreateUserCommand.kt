package arch.example.trader.cqrs.application.command

import arch.example.trader.component.ddd.Specification
import arch.example.trader.component.ddd.and

data class CreateUserCommand(
    val username: String,
    val email: String,
    val password: String,
    val firstname: String,
    val lastname: String,
) {

    init {
        if (!(validUsername and validEmail).isSatisfiedBy(this)) {
            throw IllegalStateException("Create user command is invalid")
        }
    }

    companion object {
        val validUsername = Specification<CreateUserCommand> { cmd -> cmd.username.length >= 8 &&  cmd.username.length < 64 }
        val validEmail = Specification<CreateUserCommand> { cmd -> cmd.email.contains("@") && cmd.email.length > 5 }
    }
}
