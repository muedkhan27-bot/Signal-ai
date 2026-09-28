package com.example.data.model

data class TournamentChallenge(
    val id: String = "apex_challenge_1",
    val startingEquity: Double = 10000.0,
    val currentEquity: Double = 11450.0,
    val targetProfitEquity: Double = 12500.0, // +25%
    val targetProfitPercent: Double = 25.0,
    val maxDrawdownPercent: Double = 8.0,
    val maxDrawdownEquity: Double = 9200.0,
    val peakEquity: Double = 11620.0,
    val currentDrawdownPercent: Double = 1.46,
    val status: String = "IN PROGRESS", // "IN PROGRESS", "TARGET REACHED", "BREACHED"
    val daysRemaining: Int = 18,
    val tradesCount: Int = 14,
    val winCount: Int = 12,
    val lossCount: Int = 2
) {
    val progressPercent: Float
        get() {
            val gain = currentEquity - startingEquity
            val needed = targetProfitEquity - startingEquity
            if (needed <= 0) return 1f
            return (gain / needed).coerceIn(0.0, 1.0).toFloat()
        }

    val netProfitPercent: Double
        get() = ((currentEquity - startingEquity) / startingEquity) * 100.0
}

data class MarketTicker(
    val symbol: String,
    val name: String,
    val price: Double,
    val change24h: Double,
    val assetClass: AssetClass,
    val high24h: Double,
    val low24h: Double,
    val volume: String
)
