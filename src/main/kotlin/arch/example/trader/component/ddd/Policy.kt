package arch.example.trader.component.ddd

interface Policy<T, O> {
    operator fun invoke(model: T): O
}

fun interface PredicatePolicy<T> : Policy<T, Boolean>, Specification<T> {
    override fun isSatisfiedBy(model: T): Boolean {
        return this.invoke(model)
    }
}

infix fun <T> PredicatePolicy<T>.and(other: PredicatePolicy<T>): PredicatePolicy<T> =
    PredicatePolicy<T> { model ->
        this.invoke(model) && other.invoke(model)
    }
