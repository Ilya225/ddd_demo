package arch.example.trader.layer.dto

import java.util.UUID

data class UserDto(
    val id: UUID,
    val username: String,
    val email: String,
    val password: String
)