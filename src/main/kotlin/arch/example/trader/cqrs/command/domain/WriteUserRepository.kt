package arch.example.trader.cqrs.command.domain

import arch.example.trader.cqrs.command.domain.entity.User
import arch.example.trader.cqrs.command.domain.entity.UserId
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface WriteUserRepository : CrudRepository<User, UserId>