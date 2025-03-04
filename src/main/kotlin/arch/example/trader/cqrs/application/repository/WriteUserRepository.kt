package arch.example.trader.cqrs.application.repository

import arch.example.trader.cqrs.application.command.domain.entity.User
import arch.example.trader.cqrs.application.command.domain.entity.UserId
import org.springframework.data.repository.CrudRepository

interface WriteUserRepository : CrudRepository<User, UserId> {
}