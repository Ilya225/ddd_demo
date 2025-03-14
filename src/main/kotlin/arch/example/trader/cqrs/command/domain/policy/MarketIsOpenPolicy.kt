package arch.example.trader.cqrs.command.domain.policy

import arch.example.trader.component.ddd.PredicatePolicy
import java.time.Instant
import java.time.LocalTime

class MarketIsOpenPolicy : PredicatePolicy<Instant> {

    override fun invoke(requestTime: Instant): Boolean {
        return marketIsOpen(requestTime)
    }

    private fun marketIsOpen(requestTime: Instant): Boolean {
        val marketOpen = LocalTime.of(9, 30)
        val marketClose = LocalTime.of(16, 0)

        return LocalTime.from(requestTime) in marketOpen..marketClose
    }

}