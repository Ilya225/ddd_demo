package arch.example.trader.layer.entity

import java.util.UUID

data class Stock(
    val id: UUID,
    var symbol: String,
    var description: String
)