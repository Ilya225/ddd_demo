package arch.example.trader.cqrs.application.command.domain.entity

import java.math.BigDecimal
import java.util.UUID

@JvmInline
value class StockId(val id: UUID)

data class Stock(
    val id: StockId,
    val price: BigDecimal,

)