package arch.example.trader.cqrs.query.repository

import arch.example.trader.cqrs.command.domain.entity.User
import arch.example.trader.cqrs.command.domain.entity.UserId
import org.springframework.data.repository.PagingAndSortingRepository

interface ReadUserRepository : PagingAndSortingRepository<User, UserId> {
    fun existsByEmailOrUsername(email: String, username: String): Boolean
}