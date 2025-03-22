package arch.example.trader.hexagon.domain.entity

import java.util.*

@JvmInline
value class AssetId(val id: UUID)

data class Asset(
    val id: AssetId,
    val type: AssetType,
    val symbol: String
)

enum class AssetType {
    STOCK, OPTION, FUTURE, COMMODITY, FOREX, CRYPTO
}

