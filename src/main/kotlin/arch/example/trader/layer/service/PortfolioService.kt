package arch.example.trader.layer.service

import arch.example.trader.layer.domain.Portfolio
import arch.example.trader.layer.repository.PortfolioRepository
import java.util.*

class PortfolioService(
    private val portfolioRepository: PortfolioRepository
) {
    fun transferAssets(sellerId: UUID, buyerId: UUID, assetId: UUID, quantity: Long): Boolean {
        val sellerPortfolio = portfolioRepository.findByTraderId(sellerId) ?: return false
        val buyerPortfolio = portfolioRepository.findByTraderId(buyerId) ?: return false

        return if (deductAssetsFromSeller(sellerPortfolio, assetId, quantity)) {
            addAssetsToBuyer(buyerPortfolio, assetId, quantity)
            portfolioRepository.save(sellerPortfolio)
            portfolioRepository.save(buyerPortfolio)
            true
        } else {
            false
        }
    }

    fun getAssetQuantity(traderId: UUID, assetId: UUID): Long {
        return portfolioRepository.findByTraderId(traderId)?.assets?.get(assetId) ?: 0
    }

    private fun hasEnoughAsset(sellerPortfolio: Portfolio, assetId: UUID, quantity: Long): Boolean {
        return sellerPortfolio.assets[assetId] != null && sellerPortfolio.assets[assetId]!! >= quantity
    }

    private fun deductAssetsFromSeller(portfolio: Portfolio, assetId: UUID, quantity: Long): Boolean {
        if (hasEnoughAsset(portfolio, assetId, quantity)) {

            val currentQuantity = portfolio.assets.getOrDefault(assetId, 0)
            portfolio.assets[assetId] = currentQuantity - quantity
            return true
        }
        return false
    }

    private fun addAssetsToBuyer(portfolio: Portfolio, assetId: UUID, quantity: Long) {
        portfolio.assets[assetId] = portfolio.assets.getOrDefault(assetId, 0) + quantity
    }
}