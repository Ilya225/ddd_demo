package arch.example.trader.hexagon.domain.port.outgoing

import arch.example.trader.hexagon.domain.entity.AssetId
import arch.example.trader.hexagon.domain.entity.IPO


interface IPORepository {
    fun findOneByTradableId(id: AssetId): IPO?
}