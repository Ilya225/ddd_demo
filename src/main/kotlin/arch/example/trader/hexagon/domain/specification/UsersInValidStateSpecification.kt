package arch.example.trader.hexagon.domain.specification

import arch.example.trader.component.ddd.Specification
import arch.example.trader.component.ddd.and
import arch.example.trader.hexagon.domain.entity.User
import arch.example.trader.hexagon.domain.entity.User.Companion.validEmail
import arch.example.trader.hexagon.domain.entity.User.Companion.validUsername

class UsersInValidStateSpecification : Specification<User> {

    override fun isSatisfiedBy(model: User): Boolean {
        return (validUsername and validEmail)
            .isSatisfiedBy(model)
    }
}