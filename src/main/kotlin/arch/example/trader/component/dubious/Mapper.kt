package arch.example.trader.component.dubious

fun interface Mapper<I,O> {
    fun map(input: I): O
}