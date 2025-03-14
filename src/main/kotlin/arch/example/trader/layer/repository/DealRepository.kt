package arch.example.trader.layer.repository

import arch.example.trader.layer.domain.Deal
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.UUID

interface DealRepository: PagingAndSortingRepository<Deal, UUID>, CrudRepository<Deal, UUID> {
    fun findByTradableIdOrderByCreatedAtDesc(tradableId: UUID): Deal?
}