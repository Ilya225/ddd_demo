package arch.example.trader.layer.repository

import arch.example.trader.layer.domain.Order
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.UUID

interface OrderRepository: PagingAndSortingRepository<Order, UUID>, CrudRepository<Order, UUID> {
}