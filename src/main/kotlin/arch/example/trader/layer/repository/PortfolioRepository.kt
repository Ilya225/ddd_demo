package arch.example.trader.layer.repository

import arch.example.trader.layer.domain.Portfolio
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.UUID

interface PortfolioRepository: PagingAndSortingRepository<Portfolio, UUID>, CrudRepository<Portfolio, UUID> {
    fun findByTraderId(traderId: UUID): Portfolio?
}