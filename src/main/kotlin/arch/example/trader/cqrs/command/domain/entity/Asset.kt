package arch.example.trader.cqrs.command.domain.entity

data class Asset(
    override val id: AssetId,
    override val type: AssetType
): Tradable