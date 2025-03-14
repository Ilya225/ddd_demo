package arch.example.trader.layer.domain

import java.time.Instant
import java.util.UUID

data class User(
    val id: UUID,
    var username: String,
    var email: String,
    var password: String,
    var firstname: String = "",
    var lastname: String = "",
    var createdAt: Instant = Instant.now()
)
