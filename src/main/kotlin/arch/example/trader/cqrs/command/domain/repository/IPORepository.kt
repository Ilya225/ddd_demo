package arch.example.trader.cqrs.command.domain.repository

import arch.example.trader.cqrs.command.domain.entity.AssetId
import arch.example.trader.cqrs.command.domain.entity.IPO


interface IPORepository {
    fun findOneByTradableId(id: AssetId): IPO?
}