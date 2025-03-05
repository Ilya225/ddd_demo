package arch.example.trader.hexagon.application.port.incoming

import arch.example.trader.hexagon.domain.entity.User
import arch.example.trader.hexagon.domain.entity.UserId

interface CreateUserAlwaysValidPort {
    fun createUser(user: User): UserId
}