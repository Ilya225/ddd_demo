package arch.example.trader.component.cqrs.handler

interface CommandHandler<C, R> {
    fun handle(cmd: C): R
}

interface UnitCommandHandler<C> : CommandHandler<C, Unit>