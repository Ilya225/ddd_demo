package arch.example.trader.hexagon.domain.entity

import java.math.BigDecimal

data class Portfolio(
    val traderId: UserId,
    val assets: Map<AssetId, BigDecimal> = mapOf()
) {
    fun hasEnoughAssets(assetId: AssetId, quantity: BigDecimal): Boolean {
        return assets.getOrDefault(assetId, BigDecimal(0.0)) >= quantity
    }

    fun deductAssets(assetId: AssetId, quantity: BigDecimal): Portfolio {
        if (hasEnoughAssets(assetId, quantity)) {
            val updateAssets = assets.toMutableMap()
            val asset = updateAssets.getOrDefault(assetId, BigDecimal(0.0))
            val currentQuantity = asset
            updateAssets[assetId] = currentQuantity - quantity
            return this.copy(assets = updateAssets)
        }
        throw IllegalArgumentException("Not enough assets to deduct: $assetId")
    }

    fun addAssets(assetId: AssetId, quantity: BigDecimal): Portfolio {
        val updateAssets = assets.toMutableMap()
        updateAssets[assetId] = assets.getOrDefault(assetId, BigDecimal(0.0)) + quantity
        return this.copy(assets = updateAssets)
    }

    fun getQuantity(assetId: AssetId): BigDecimal {
        return assets.getOrDefault(assetId, BigDecimal(0.0))
    }
}