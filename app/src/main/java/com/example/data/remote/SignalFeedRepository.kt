package com.example.data.remote

import com.example.data.model.AssetClass
import com.example.data.model.MarketTicker
import com.example.data.model.SignalAction
import com.example.data.model.SignalStatus
import com.example.data.model.TradingSignal
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SignalFeedRepository {

    fun getInitialSignals(): List<TradingSignal> {
        return listOf(
            TradingSignal(
                id = "SIG-901",
                symbol = "BTC/USDT",
                name = "Bitcoin",
                assetClass = AssetClass.CRYPTO,
                action = SignalAction.BUY,
                currentPrice = 65420.0,
                entryPrice = 65150.0,
                stopLoss = 64100.0,
                tp1 = 66500.0,
                tp2 = 67800.0,
                tp3 = 69500.0,
                optimalDuration = "1h - 4h Scalp-Swing",
                timeframe = "15M",
                confidenceScore = 95,
                riskRewardRatio = "1 : 3.8",
                rationale = "Liquidity sweep of Asian session low + massive bullish CVD absorption at $64.8k support.",
                deepAiAnalysis = "Gemini 3.1 Pro High-Thinking identified institutional absorption on Binance & Bybit order books. Volume Delta is +38% on 15M chart. Optimal holding duration is calculated at 2.4 hours with a 95% confidence rating for TP1.",
                indicatorConfluence = listOf("Bullish FVG Retest", "RSI Hidden Divergence", "Order Flow Absorption", "VWAP Reclaim"),
                pnlPercent = 2.15,
                sparklinePoints = listOf(64100f, 64500f, 64300f, 64900f, 64800f, 65200f, 65150f, 65420f),
                isTournamentVerified = true
            ),
            TradingSignal(
                id = "SIG-902",
                symbol = "EUR/USD",
                name = "Euro / US Dollar",
                assetClass = AssetClass.FOREX,
                action = SignalAction.SELL,
                currentPrice = 1.0864,
                entryPrice = 1.0880,
                stopLoss = 1.0915,
                tp1 = 1.0830,
                tp2 = 1.0795,
                tp3 = 1.0750,
                optimalDuration = "3h - 8h Day Trade",
                timeframe = "1H",
                confidenceScore = 92,
                riskRewardRatio = "1 : 3.2",
                rationale = "Bearish rejection at London session high, retesting breaker block with strong US Treasury yield divergence.",
                deepAiAnalysis = "Macro interest rate yield differentials favor USD strength. Price tapped into 1H bearish breaker block with decreasing bull volume. Stop loss is strictly placed above today's high at 1.0915.",
                indicatorConfluence = listOf("London High Sweep", "Bearish Breaker Block", "MACD Bearish Cross", "Yield Confluence"),
                pnlPercent = 0.48,
                sparklinePoints = listOf(1.0820f, 1.0850f, 1.0875f, 1.0890f, 1.0880f, 1.0870f, 1.0864f),
                isTournamentVerified = true
            ),
            TradingSignal(
                id = "SIG-903",
                symbol = "NVDA",
                name = "NVIDIA Corporation",
                assetClass = AssetClass.STOCKS,
                action = SignalAction.BUY,
                currentPrice = 124.60,
                entryPrice = 123.80,
                stopLoss = 119.50,
                tp1 = 130.00,
                tp2 = 136.50,
                tp3 = 144.00,
                optimalDuration = "1d - 3d Swing",
                timeframe = "4H",
                confidenceScore = 94,
                riskRewardRatio = "1 : 3.6",
                rationale = "Data center revenue momentum + institutional options flow gamma squeeze setup above 122 resistance.",
                deepAiAnalysis = "Deep learning analysis reveals massive institutional call buying at $130 strike for Friday expiration. Weekly consolidation has resolved upward with above-average volume. 1 to 3 day holding duration.",
                indicatorConfluence = listOf("Options Gamma Squeeze", "Bullish Flag Breakout", "20/50 EMA Bull Cross", "Institutional Dark Pool Flow"),
                pnlPercent = 3.82,
                sparklinePoints = listOf(118f, 120f, 119.5f, 122f, 121f, 123.8f, 124.6f),
                isTournamentVerified = true
            ),
            TradingSignal(
                id = "SIG-904",
                symbol = "XAU/USD",
                name = "Gold Spot (Gold)",
                assetClass = AssetClass.COMMODITIES,
                action = SignalAction.BUY,
                currentPrice = 2438.50,
                entryPrice = 2430.00,
                stopLoss = 2412.00,
                tp1 = 2460.00,
                tp2 = 2490.00,
                tp3 = 2525.00,
                optimalDuration = "6h - 24h Day-Swing",
                timeframe = "4H",
                confidenceScore = 96,
                riskRewardRatio = "1 : 4.2",
                rationale = "Central bank reserve accumulation + geopolitical safe haven bid breaking multi-week descending wedge.",
                deepAiAnalysis = "Gold has completed a classic Wyckoff re-accumulation spring. COT (Commitment of Traders) report reveals commercial hedgers reducing short exposure while retail is under-allocated. Optimal hold period is 18 hours.",
                indicatorConfluence = listOf("Wyckoff Spring", "Descending Wedge Break", "Safe Haven Volume Surge", "COT Bullish Shift"),
                pnlPercent = 1.95,
                sparklinePoints = listOf(2405f, 2415f, 2410f, 2422f, 2430f, 2438.5f),
                isTournamentVerified = true
            ),
            TradingSignal(
                id = "SIG-905",
                symbol = "US100",
                name = "Nasdaq 100",
                assetClass = AssetClass.INDICES,
                action = SignalAction.BUY,
                currentPrice = 19850.0,
                entryPrice = 19780.0,
                stopLoss = 19620.0,
                tp1 = 20050.0,
                tp2 = 20280.0,
                tp3 = 20500.0,
                optimalDuration = "4h - 12h Intraday",
                timeframe = "1H",
                confidenceScore = 91,
                riskRewardRatio = "1 : 3.1",
                rationale = "New York market open liquidity gap filled at 19,780; semiconductor index leading rebound.",
                deepAiAnalysis = "Algorithm detects high probability liquidity bounce at previous week value area low. Mega-cap tech correlation matrix indicates breadth expansion across S&P and Nasdaq.",
                indicatorConfluence = listOf("Value Area Low Bounce", "Market Breadth +72%", "Stochastic Oversold Cross", "Tick Index Surge"),
                pnlPercent = 1.12,
                sparklinePoints = listOf(19650f, 19700f, 19680f, 19780f, 19820f, 19850f),
                isTournamentVerified = false
            ),
            TradingSignal(
                id = "SIG-906",
                symbol = "ETH/USDT",
                name = "Ethereum",
                assetClass = AssetClass.CRYPTO,
                action = SignalAction.BUY,
                currentPrice = 3490.0,
                entryPrice = 3450.0,
                stopLoss = 3380.0,
                tp1 = 3580.0,
                tp2 = 3720.0,
                tp3 = 3900.0,
                optimalDuration = "2h - 8h Swing",
                timeframe = "1H",
                confidenceScore = 90,
                riskRewardRatio = "1 : 3.5",
                rationale = "Layer 2 TVL growth + staking inflow surge holding key $3,450 psychological pivot.",
                deepAiAnalysis = "On-chain exchange reserves dropped by 45,000 ETH over the past 24 hours. Funding rates on Binance and Bybit are neutral, avoiding long liquidation cascades.",
                indicatorConfluence = listOf("Exchange Inflow Deficit", "RSI Bullish Support", "Volume Shelf Bounce", "L2 Gas Expansion"),
                pnlPercent = 1.45,
                sparklinePoints = listOf(3390f, 3420f, 3410f, 3450f, 3470f, 3490f),
                isTournamentVerified = false
            ),
            TradingSignal(
                id = "SIG-907",
                symbol = "GBP/JPY",
                name = "British Pound / Japanese Yen",
                assetClass = AssetClass.FOREX,
                action = SignalAction.SELL,
                currentPrice = 201.20,
                entryPrice = 201.80,
                stopLoss = 202.50,
                tp1 = 200.40,
                tp2 = 199.50,
                tp3 = 198.00,
                optimalDuration = "4h - 14h Day Trade",
                timeframe = "1H",
                confidenceScore = 93,
                riskRewardRatio = "1 : 3.4",
                rationale = "Bank of Japan policy rate hike speculation sparking rapid JPY unwinding carry trades.",
                deepAiAnalysis = "Dragon pair (GBP/JPY) rejected at major psychological resistance of 202.00. 15-minute chart exhibits a classic head & shoulders top with bearish volume on the right shoulder breakdown.",
                indicatorConfluence = listOf("H&S Reversal Pattern", "BoJ Intervention Threat", "Overbought Stochastic", "Key Resistance Rejection"),
                pnlPercent = 0.65,
                sparklinePoints = listOf(200.5f, 201.0f, 201.8f, 202.1f, 201.7f, 201.2f),
                isTournamentVerified = true
            )
        )
    }

    fun getMarketTickers(): List<MarketTicker> {
        return listOf(
            MarketTicker("BTC/USDT", "Bitcoin", 65420.0, +3.42, AssetClass.CRYPTO, 66100.0, 63800.0, "$28.4B"),
            MarketTicker("ETH/USDT", "Ethereum", 3490.0, +2.18, AssetClass.CRYPTO, 3540.0, 3390.0, "$14.2B"),
            MarketTicker("SOL/USDT", "Solana", 154.20, +5.82, AssetClass.CRYPTO, 158.0, 145.1, "$4.1B"),
            MarketTicker("EUR/USD", "Euro / USD", 1.0864, -0.24, AssetClass.FOREX, 1.0895, 1.0850, "$420B"),
            MarketTicker("GBP/USD", "Cable", 1.2940, +0.12, AssetClass.FOREX, 1.2980, 1.2910, "$210B"),
            MarketTicker("USD/JPY", "Yen", 157.45, -0.45, AssetClass.FOREX, 158.20, 156.90, "$340B"),
            MarketTicker("NVDA", "Nvidia", 124.60, +4.15, AssetClass.STOCKS, 126.10, 119.80, "$32.5B"),
            MarketTicker("AAPL", "Apple", 218.40, +1.05, AssetClass.STOCKS, 220.00, 216.50, "$18.2B"),
            MarketTicker("TSLA", "Tesla", 248.80, +3.60, AssetClass.STOCKS, 252.00, 239.50, "$15.8B"),
            MarketTicker("XAU/USD", "Gold Spot", 2438.50, +1.28, AssetClass.COMMODITIES, 2445.0, 2415.0, "$85B"),
            MarketTicker("USOIL", "Crude Oil WTI", 81.25, -0.75, AssetClass.COMMODITIES, 82.40, 80.50, "$45B"),
            MarketTicker("US500", "S&P 500", 5520.0, +0.82, AssetClass.INDICES, 5545.0, 5480.0, "$120B"),
            MarketTicker("US100", "Nasdaq 100", 19850.0, +1.45, AssetClass.INDICES, 19940.0, 19680.0, "$98B")
        )
    }

    // Stream simulated live price fluctuations
    fun streamLiveTickerUpdates(currentTickers: List<MarketTicker>): Flow<List<MarketTicker>> = flow {
        var list = currentTickers
        while (true) {
            delay(3500)
            list = list.map { ticker ->
                val deltaPercent = ((-15..15).random() / 1000.0)
                val newPrice = ticker.price * (1.0 + deltaPercent)
                val newChange = ticker.change24h + (deltaPercent * 10)
                ticker.copy(
                    price = Math.round(newPrice * 100.0) / 100.0,
                    change24h = Math.round(newChange * 100.0) / 100.0
                )
            }
            emit(list)
        }
    }
}
