package arch.example.trader.layer.repository

import arch.example.trader.layer.domain.Wallet
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.UUID

interface WalletRepository:  PagingAndSortingRepository<Wallet, UUID>, CrudRepository<Wallet, UUID>