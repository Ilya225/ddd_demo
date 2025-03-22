package arch.example.trader.component.ddd.vo

import java.math.BigDecimal

data class Ratio(
    val ratio: BigDecimal
) {
    init {
        require(ratio >= BigDecimal(0) && ratio <= BigDecimal(1)) { "Ratio can't be less than zero or greater than 1" }
    }
}
