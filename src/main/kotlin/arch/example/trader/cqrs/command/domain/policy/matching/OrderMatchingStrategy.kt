package arch.example.trader.cqrs.command.domain.policy.matching

import arch.example.trader.component.ddd.Policy
import arch.example.trader.cqrs.command.domain.entity.Deal
import arch.example.trader.cqrs.command.domain.entity.Order

interface OrderMatchingStrategy : Policy<Order, List<Deal>>