package arch.example.trader.hexagon.domain.entity

sealed interface AssetType<T> where T : Asset {
    val underlyings: Set<Class<out Asset>>
}

enum class AssetCategory(override val underlyings: Set<Class<out Asset>>) : AssetType<Asset> {
    SECURITY(setOf(
        Stock::class.java
    )),

    COMMODITY(setOf(
        Future::class.java,
        Option::class.java,
        Energy::class.java
    ));
}