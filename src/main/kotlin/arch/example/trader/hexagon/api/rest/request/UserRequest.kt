package arch.example.trader.hexagon.api.rest.request


data class UserRequest(
    val username: String,
    val email: String,
    val password: String,
    val firstname: String,
    val lastname: String,
)
