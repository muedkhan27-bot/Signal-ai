package com.example.data.local

import com.example.data.model.AssetClass
import com.example.data.model.SignalAction
import com.example.data.model.SignalStatus
import com.example.data.model.TradingSignal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class TradeRepository(private val dao: TradeHistoryDao) {

    val allTrades: Flow<List<TradeSignalEntity>> = dao.getAllTrades()
    val tournamentTrades: Flow<List<TradeSignalEntity>> = dao.getTournamentTrades()

    suspend fun logTrade(signal: TradingSignal, platform: String, isTournament: Boolean = false) {
        val entity = TradeSignalEntity(
            id = signal.id,
            symbol = signal.symbol,
            name = signal.name,
            assetClass = signal.assetClass.name,
            action = signal.action.name,
            entryPrice = signal.entryPrice,
            stopLoss = signal.stopLoss,
            tp1 = signal.tp1,
            tp2 = signal.tp2,
            tp3 = signal.tp3,
            optimalDuration = signal.optimalDuration,
            timeframe = signal.timeframe,
            confidenceScore = signal.confidenceScore,
            riskRewardRatio = signal.riskRewardRatio,
            rationale = signal.rationale,
            timestamp = signal.timestamp,
            status = signal.status.name,
            pnlPercent = signal.pnlPercent,
            executedPlatform = platform,
            isTournamentTrade = isTournament
        )
        dao.insertTrade(entity)
    }

    suspend fun seedSampleHistoryIfEmpty() {
        val existing = dao.getAllTrades().first()
        if (existing.isEmpty()) {
            val sampleTrades = listOf(
                TradeSignalEntity(
                    id = "tr_001",
                    symbol = "BTC/USDT",
                    name = "Bitcoin",
                    assetClass = AssetClass.CRYPTO.name,
                    action = SignalAction.BUY.name,
                    entryPrice = 64200.0,
                    stopLoss = 63100.0,
                    tp1 = 65800.0,
                    tp2 = 67200.0,
                    tp3 = 69000.0,
                    optimalDuration = "2h - 6h Swing",
                    timeframe = "1H",
                    confidenceScore = 94,
                    riskRewardRatio = "1 : 3.8",
                    rationale = "Liquidity sweep below key S/R followed by strong 4H bullish engulfing candle and high volume delta.",
                    timestamp = System.currentTimeMillis() - 7200000,
                    status = SignalStatus.TP2_HIT.name,
                    pnlPercent = 4.67,
                    executedPlatform = "Binance Futures & Spot",
                    isTournamentTrade = true
                ),
                TradeSignalEntity(
                    id = "tr_002",
                    symbol = "EUR/USD",
                    name = "Euro / US Dollar",
                    assetClass = AssetClass.FOREX.name,
                    action = SignalAction.SELL.name,
                    entryPrice = 1.0892,
                    stopLoss = 1.0925,
                    tp1 = 1.0845,
                    tp2 = 1.0810,
                    tp3 = 1.0770,
                    optimalDuration = "4h - 12h Day Trade",
                    timeframe = "4H",
                    confidenceScore = 91,
                    riskRewardRatio = "1 : 2.5",
                    rationale = "Bearish rejection at London session high, retesting bearish order block on 1H chart with RSI divergence.",
                    timestamp = System.currentTimeMillis() - 18000000,
                    status = SignalStatus.TP2_HIT.name,
                    pnlPercent = 0.75,
                    executedPlatform = "MetaTrader 5",
                    isTournamentTrade = false
                ),
                TradeSignalEntity(
                    id = "tr_003",
                    symbol = "NVDA",
                    name = "NVIDIA Corporation",
                    assetClass = AssetClass.STOCKS.name,
                    action = SignalAction.BUY.name,
                    entryPrice = 118.50,
                    stopLoss = 114.20,
                    tp1 = 124.80,
                    tp2 = 131.00,
                    tp3 = 138.50,
                    optimalDuration = "1d - 3d Swing",
                    timeframe = "1D",
                    confidenceScore = 96,
                    riskRewardRatio = "1 : 3.2",
                    rationale = "Golden cross on 50 EMA / 200 EMA + institutional dark pool accumulation surge.",
                    timestamp = System.currentTimeMillis() - 86400000,
                    status = SignalStatus.TP3_HIT.name,
                    pnlPercent = 10.55,
                    executedPlatform = "TradingView",
                    isTournamentTrade = true
                ),
                TradeSignalEntity(
                    id = "tr_004",
                    symbol = "XAU/USD",
                    name = "Gold Spot",
                    assetClass = AssetClass.COMMODITIES.name,
                    action = SignalAction.BUY.name,
                    entryPrice = 2415.80,
                    stopLoss = 2398.00,
                    tp1 = 2445.00,
                    tp2 = 2470.00,
                    tp3 = 2500.00,
                    optimalDuration = "6h - 24h Day Trade",
                    timeframe = "4H",
                    confidenceScore = 93,
                    riskRewardRatio = "1 : 3.1",
                    rationale = "Safe haven breakout above multi-week descending channel with massive open interest spike.",
                    timestamp = System.currentTimeMillis() - 120000000,
                    status = SignalStatus.TP2_HIT.name,
                    pnlPercent = 2.24,
                    executedPlatform = "MetaTrader 4",
                    isTournamentTrade = true
                ),
                TradeSignalEntity(
                    id = "tr_005",
                    symbol = "US500",
                    name = "S&P 500 Index",
                    assetClass = AssetClass.INDICES.name,
                    action = SignalAction.BUY.name,
                    entryPrice = 5480.0,
                    stopLoss = 5440.0,
                    tp1 = 5540.0,
                    tp2 = 5600.0,
                    tp3 = 5650.0,
                    optimalDuration = "1d - 4d Swing",
                    timeframe = "4H",
                    confidenceScore = 89,
                    riskRewardRatio = "1 : 2.8",
                    rationale = "Trend continuation bounce off 21 EMA support on daily timeframe with bullish market breadth.",
                    timestamp = System.currentTimeMillis() - 172800000,
                    status = SignalStatus.TP1_HIT.name,
                    pnlPercent = 1.09,
                    executedPlatform = "Interactive Brokers (TWS)",
                    isTournamentTrade = false
                ),
                TradeSignalEntity(
                    id = "tr_006",
                    symbol = "SOL/USDT",
                    name = "Solana",
                    assetClass = AssetClass.CRYPTO.name,
                    action = SignalAction.BUY.name,
                    entryPrice = 142.50,
                    stopLoss = 136.00,
                    tp1 = 152.00,
                    tp2 = 164.00,
                    tp3 = 175.00,
                    optimalDuration = "3h - 8h Swing",
                    timeframe = "1H",
                    confidenceScore = 92,
                    riskRewardRatio = "1 : 3.3",
                    rationale = "Breakout of ascending triangle with surging DEX volume and open interest expansion.",
                    timestamp = System.currentTimeMillis() - 250000000,
                    status = SignalStatus.TP2_HIT.name,
                    pnlPercent = 15.09,
                    executedPlatform = "Bybit Derivatives",
                    isTournamentTrade = true
                )
            )
            dao.insertTrades(sampleTrades)
        }
    }
}
