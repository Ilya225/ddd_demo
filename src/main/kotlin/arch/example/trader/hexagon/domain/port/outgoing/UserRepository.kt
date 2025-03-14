package arch.example.trader.hexagon.domain.port.outgoing

import arch.example.trader.hexagon.domain.entity.User
import arch.example.trader.hexagon.domain.entity.UserId

interface UserRepository {
    fun save(user: User): UserId

    fun existsByEmailOrUsername(email: String, username: String): Boolean
}