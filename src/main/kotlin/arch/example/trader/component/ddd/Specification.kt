package arch.example.trader.component.ddd

fun interface Specification<T> {
    fun isSatisfiedBy(model: T): Boolean
}

infix fun <T> Specification<T>.and(spec: Specification<T>): Specification<T> =
    Specification { model ->
        this.isSatisfiedBy(model) && spec.isSatisfiedBy(model)
    }
