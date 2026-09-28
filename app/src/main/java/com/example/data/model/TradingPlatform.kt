package com.example.data.model

enum class TradingPlatform(
    val id: String,
    val displayName: String,
    val subtitle: String,
    val category: String,
    val iconName: String
) {
    METATRADER_5(
        id = "mt5",
        displayName = "MetaTrader 5",
        subtitle = "Multi-asset Forex & CFDs institutional platform",
        category = "Forex / CFDs",
        iconName = "mt5"
    ),
    METATRADER_4(
        id = "mt4",
        displayName = "MetaTrader 4",
        subtitle = "Classic algorithmic Forex terminal",
        category = "Forex / CFDs",
        iconName = "mt4"
    ),
    TRADINGVIEW(
        id = "tradingview",
        displayName = "TradingView",
        subtitle = "PineScript alerts, webhooks & multi-broker routing",
        category = "Charting & Webhook",
        iconName = "tv"
    ),
    BINANCE(
        id = "binance",
        displayName = "Binance Futures & Spot",
        subtitle = "USDT-M Perpetual & Spot crypto orders",
        category = "Crypto Exchange",
        iconName = "binance"
    ),
    COINBASE(
        id = "coinbase",
        displayName = "Coinbase Advanced",
        subtitle = "Direct spot execution & institutional order book",
        category = "Crypto Exchange",
        iconName = "coinbase"
    ),
    BYBIT(
        id = "bybit",
        displayName = "Bybit Derivatives",
        subtitle = "Unified margin & crypto perpetuals",
        category = "Crypto Exchange",
        iconName = "bybit"
    ),
    KRAKEN(
        id = "kraken",
        displayName = "Kraken Pro",
        subtitle = "High liquidity crypto spot & margin",
        category = "Crypto Exchange",
        iconName = "kraken"
    ),
    ETORO(
        id = "etoro",
        displayName = "eToro",
        subtitle = "Fractional shares, copy trading & CFD leverage",
        category = "Social & Broker",
        iconName = "etoro"
    ),
    INTERACTIVE_BROKERS(
        id = "ibkr",
        displayName = "Interactive Brokers (TWS)",
        subtitle = "Global equities, futures, options & FX",
        category = "Institutional Broker",
        iconName = "ibkr"
    );

    fun formatOrderCopy(signal: TradingSignal, lotOrUnits: String): String {
        return when (this) {
            METATRADER_5, METATRADER_4 -> {
                """
                === ${this.displayName} ORDER TICKET ===
                Symbol: ${signal.symbol}
                Type: ${if (signal.action == SignalAction.BUY) "BUY LIMIT / BUY MARKET" else "SELL LIMIT / SELL MARKET"}
                Volume (Lots): $lotOrUnits
                Open Price: ${signal.entryPrice}
                Stop Loss (S/L): ${signal.stopLoss}
                Take Profit 1 (T/P): ${signal.tp1}
                Take Profit 2 (T/P): ${signal.tp2}
                Take Profit 3 (T/P): ${signal.tp3}
                Comment: ApexSignal-AI_${signal.id}
                Horizon: ${signal.optimalDuration}
                """.trimIndent()
            }
            TRADINGVIEW -> {
                """
                {
                  "ticker": "${signal.symbol}",
                  "action": "${if (signal.action == SignalAction.BUY) "buy" else "sell"}",
                  "order_type": "limit",
                  "price": ${signal.entryPrice},
                  "stop_loss": ${signal.stopLoss},
                  "take_profit_1": ${signal.tp1},
                  "take_profit_2": ${signal.tp2},
                  "take_profit_3": ${signal.tp3},
                  "timeframe": "${signal.timeframe}",
                  "duration": "${signal.optimalDuration}",
                  "source": "ApexSignal_AI"
                }
                """.trimIndent()
            }
            BINANCE, BYBIT -> {
                """
                === ${this.displayName} DERIVATIVES ===
                Pair: ${signal.symbol}
                Side: ${if (signal.action == SignalAction.BUY) "LONG / OPEN BUY" else "SHORT / OPEN SELL"}
                Trigger Price: ${signal.entryPrice} USDT
                Position Size: $lotOrUnits
                SL Trigger: ${signal.stopLoss} USDT
                TP Target 1: ${signal.tp1} USDT
                TP Target 2: ${signal.tp2} USDT
                Holding Target: ${signal.optimalDuration}
                Confidence: ${signal.confidenceScore}%
                """.trimIndent()
            }
            COINBASE, KRAKEN -> {
                """
                === ${this.displayName} ORDER ===
                Market: ${signal.symbol}
                Action: ${if (signal.action == SignalAction.BUY) "BUY" else "SELL"}
                Limit Price: ${signal.entryPrice}
                Size: $lotOrUnits
                Stop Loss: ${signal.stopLoss}
                Target TP: ${signal.tp1}
                """.trimIndent()
            }
            ETORO, INTERACTIVE_BROKERS -> {
                """
                === ${this.displayName} TICKET ===
                Asset: ${signal.symbol}
                Direction: ${if (signal.action == SignalAction.BUY) "BUY (Going Long)" else "SELL (Going Short)"}
                Entry Rate: ${signal.entryPrice}
                Stop Loss Rate: ${signal.stopLoss}
                Take Profit Rate: ${signal.tp1}
                Duration Estimate: ${signal.optimalDuration}
                """.trimIndent()
            }
        }
    }
}
