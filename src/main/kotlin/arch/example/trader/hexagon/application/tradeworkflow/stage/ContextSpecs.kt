package arch.example.trader.hexagon.application.tradeworkflow.stage

import arch.example.trader.component.ddd.Specification


val hasNewOrderRequest = Specification<TradeWorkflowContext> { it.newOrderRequest != null }
val hasOrder = Specification<TradeWorkflowContext> { it.order != null }
val hasAtLeastOneDeal = Specification<TradeWorkflowContext> { it.deals.isNotEmpty() }
val hasBuyerFee = Specification<TradeWorkflowContext> { it.buyerFee != null && it.buyerFee.notZero() }
val hasSellerFee = Specification<TradeWorkflowContext> { it.sellerFee != null && it.sellerFee.notZero() }
val hasBuyerDiscount = Specification<TradeWorkflowContext> { it.buyerDiscount != null }
val hasSellerDiscount = Specification<TradeWorkflowContext> { it.sellerDiscount != null }
val hasAtLeastOnePayment = Specification<TradeWorkflowContext> { it.deals.isNotEmpty() }
val hasAtLeastOneTransferredAsset = Specification<TradeWorkflowContext> { it.transferredAssets.isNotEmpty() }
val alwaysValid = Specification<TradeWorkflowContext> { true }

