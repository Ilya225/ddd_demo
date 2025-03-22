package arch.example.trader.layer.service

import arch.example.trader.layer.domain.Wallet
import arch.example.trader.layer.repository.WalletRepository
import java.math.BigDecimal
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

class WalletService(
    private val walletRepository: WalletRepository
) {

    fun findUserWallet(userId: UUID): Wallet? {
        return walletRepository.findById(userId).getOrNull()
    }

    fun debit(walletId: UUID, amount: BigDecimal) {
        val wallet = walletRepository.findById(walletId).get()
        val newBalance = wallet.balance - amount

        walletRepository.save(wallet.copy(balance = newBalance))
    }

    fun credit(walletId: UUID, amount: BigDecimal) {
        val wallet = walletRepository.findById(walletId).get()
        val newBalance = wallet.balance + amount

        walletRepository.save(wallet.copy(balance = newBalance))
    }
}