package arch.example.trader.layer.repository

import arch.example.trader.layer.domain.User
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : PagingAndSortingRepository<User, UUID>, CrudRepository<User, UUID>