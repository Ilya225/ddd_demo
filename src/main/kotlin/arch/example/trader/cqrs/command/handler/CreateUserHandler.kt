package arch.example.trader.cqrs.command.handler

import arch.example.trader.component.cqrs.handler.CommandHandler
import arch.example.trader.cqrs.command.input.CreateUserCommand
import arch.example.trader.cqrs.command.domain.policy.UserShouldNotExistPolicy
import arch.example.trader.cqrs.command.domain.entity.User
import arch.example.trader.cqrs.command.domain.entity.UserId
import arch.example.trader.cqrs.command.domain.WriteUserRepository
import org.springframework.stereotype.Service
import org.springframework.util.IdGenerator
import java.time.Instant

@Service
class CreateUserHandler(
    private val writeUserRepository: WriteUserRepository,
    private val policy: UserShouldNotExistPolicy,
    private val idGenerator: IdGenerator
) : CommandHandler<CreateUserCommand, UserId> {

    override fun handle(cmd: CreateUserCommand): UserId {

        val user = User(
            UserId(idGenerator.generateId()),
            cmd.username,
            cmd.email,
            cmd.password,
            cmd.firstname,
            cmd.lastname,
            Instant.now()
        )

        if (!policy(user)) {
            throw IllegalArgumentException("User is already exists")
        }

        return writeUserRepository.save(user).id
    }
}