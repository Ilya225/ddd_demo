package arch.example.trader.hexagon.domain.port.outgoing

import arch.example.trader.hexagon.domain.entity.UserId
import arch.example.trader.hexagon.domain.entity.Wallet

interface WalletRepository {
    fun findByUserId(userId: UserId): Wallet?
}