package arch.example.trader.component.cqrs.resolver

interface QueryResolver<Q, R> {
    fun resolve(query: Q): R
}