package arch.example.trader.layer.repository

import arch.example.trader.layer.entity.User
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.UUID

interface StockRepository: PagingAndSortingRepository<User, UUID>, CrudRepository<User, UUID> {
}