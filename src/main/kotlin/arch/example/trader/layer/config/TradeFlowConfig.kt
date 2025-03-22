package arch.example.trader.layer.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.math.BigDecimal

@Configuration
@ConfigurationProperties("trade.flow")
data class TradeFlowConfig(
    val buyFee: BigDecimal = BigDecimal(0.01),
    val sellFee: BigDecimal = BigDecimal(0.005)
)