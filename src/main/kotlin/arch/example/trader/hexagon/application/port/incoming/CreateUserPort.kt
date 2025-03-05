package arch.example.trader.hexagon.application.port.incoming

import arch.example.trader.hexagon.domain.entity.UserId

interface CreateUserPort {
    fun createUser(username: String, email: String, password: String): UserId
}