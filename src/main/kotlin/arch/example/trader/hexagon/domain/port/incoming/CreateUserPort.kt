package arch.example.trader.hexagon.domain.port.incoming

import arch.example.trader.hexagon.domain.entity.UserId

interface CreateUserPort {
    fun createUser(username: String, email: String, password: String): UserId
}