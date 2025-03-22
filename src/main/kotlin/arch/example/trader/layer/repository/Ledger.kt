package arch.example.trader.layer.repository

import arch.example.trader.layer.domain.LedgerRecord
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.UUID

interface Ledger: PagingAndSortingRepository<LedgerRecord, UUID>, CrudRepository<LedgerRecord, UUID> {


}