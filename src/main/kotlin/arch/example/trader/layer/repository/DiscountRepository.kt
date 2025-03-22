package arch.example.trader.layer.repository

import arch.example.trader.layer.domain.Discount
import arch.example.trader.layer.domain.OrderType
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.UUID

interface DiscountRepository: PagingAndSortingRepository<Discount, UUID>, CrudRepository<Discount, UUID> {

    fun findByUserIdAndOrderType(userId: UUID, orderType: OrderType): Discount?
}