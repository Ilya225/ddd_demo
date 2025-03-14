package arch.example.trader.layer.repository

import arch.example.trader.layer.domain.IPO
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.UUID

interface IPORepository: PagingAndSortingRepository<IPO, UUID>, CrudRepository<IPO, UUID> {
    fun findOneByTradableId(tradableId: UUID): IPO?
}