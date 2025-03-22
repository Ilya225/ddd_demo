package arch.example.trader.layer.service

import arch.example.trader.layer.domain.Deal
import arch.example.trader.layer.domain.Order
import arch.example.trader.layer.repository.DealRepository
import java.time.Instant
import java.util.UUID

class DealService(
    private val dealRepository: DealRepository
) {
    fun makeADeal(buyOrder: Order, sellOrder: Order): Deal {
        val matchedQuantity = minOf(buyOrder.quantity, sellOrder.quantity)
        val sealedDeal = Deal(UUID.randomUUID(), sellOrder.id, buyOrder.id, buyOrder.assetId,
            matchedQuantity, sellOrder.unitPrice, Instant.now())

        dealRepository.save(sealedDeal)
        return sealedDeal
    }
}
