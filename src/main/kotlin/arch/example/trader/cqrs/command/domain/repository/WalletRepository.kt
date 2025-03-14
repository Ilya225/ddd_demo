package arch.example.trader.cqrs.command.domain.repository

import arch.example.trader.cqrs.command.domain.entity.UserId
import arch.example.trader.cqrs.command.domain.entity.Wallet

interface WalletRepository {
    fun findByUserId(userId: UserId): Wallet?
}