package arch.example.trader.component.ddd

interface Policy<T, O> {
    operator fun invoke(model: T): O
}

interface PredicatePolicy<T> : Policy<T, Boolean>, Specification<T> {
    override fun isSatisfiedBy(model: T): Boolean {
        return this.invoke(model)
    }
}