package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.LiveTickerRibbon
import com.example.ui.screens.DeepThinkingScreen
import com.example.ui.screens.DevelopmentTimelineScreen
import com.example.ui.screens.RiskCalculatorScreen
import com.example.ui.screens.SignalsScreen
import com.example.ui.screens.TournamentScreen
import com.example.ui.screens.TradeHistoryScreen
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.EmeraldBull
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.RadiantGold
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.ui.viewmodel.MainViewModel

enum class NavigationScreen(val label: String, val icon: ImageVector) {
    SIGNALS("Signals", Icons.Default.Sensors),
    THINKING("Thinking", Icons.Default.Psychology),
    TOURNAMENT("Tournament", Icons.Default.EmojiEvents),
    RISK("Risk Calc", Icons.Default.Calculate),
    HISTORY("History", Icons.Default.Assessment),
    TIMELINE("Timeline", Icons.Default.Timeline)
}

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppScreen(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(viewModel: MainViewModel) {
    var currentScreen by remember { mutableStateOf(NavigationScreen.SIGNALS) }
    val tickers by viewModel.marketTickers.collectAsState()
    val platform by viewModel.selectedPlatform.collectAsState()

    // Handle back button to return to SIGNALS home
    BackHandler(enabled = currentScreen != NavigationScreen.SIGNALS) {
        currentScreen = NavigationScreen.SIGNALS
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground),
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .background(
                                        Brush.linearGradient(listOf(NeonCyan, ElectricPurple)),
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = "Logo",
                                    tint = Color.Black,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "APEX",
                                        color = TextPrimary,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Black,
                                        letterSpacing = 1.sp
                                    )
                                    Text(
                                        text = "SIGNAL",
                                        color = NeonCyan,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Black,
                                        letterSpacing = 1.sp
                                    )
                                    Text(
                                        text = " AI",
                                        color = RadiantGold,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                                Text(
                                    text = "Universal AI Signal Engine • ${platform.displayName}",
                                    color = TextSecondary,
                                    fontSize = 10.sp,
                                    maxLines = 1
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = DarkBackground,
                        titleContentColor = TextPrimary
                    )
                )

                // Live Ticker Ribbon
                LiveTickerRibbon(tickers = tickers)
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF0A0F1D),
                tonalElevation = 8.dp,
                modifier = Modifier
                    .border(width = 0.5.dp, color = DarkBorder)
                    .testTag("bottom_nav_bar")
            ) {
                NavigationScreen.entries.forEach { screen ->
                    val isSelected = currentScreen == screen
                    val iconColor = when (screen) {
                        NavigationScreen.THINKING -> ElectricPurple
                        NavigationScreen.TOURNAMENT -> RadiantGold
                        NavigationScreen.SIGNALS -> NeonCyan
                        else -> NeonCyan
                    }

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { currentScreen = screen },
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.label,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = {
                            Text(
                                text = screen.label,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = iconColor,
                            selectedTextColor = iconColor,
                            unselectedIconColor = TextTertiary,
                            unselectedTextColor = TextTertiary,
                            indicatorColor = Color(0xFF131D31)
                        ),
                        modifier = Modifier.testTag("nav_item_${screen.name.lowercase()}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DarkBackground)
        ) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "ScreenTransition"
            ) { screen ->
                when (screen) {
                    NavigationScreen.SIGNALS -> SignalsScreen(
                        viewModel = viewModel,
                        onNavigateToThinking = { currentScreen = NavigationScreen.THINKING }
                    )
                    NavigationScreen.THINKING -> DeepThinkingScreen(
                        viewModel = viewModel
                    )
                    NavigationScreen.TOURNAMENT -> TournamentScreen(
                        viewModel = viewModel,
                        onNavigateToThinking = { currentScreen = NavigationScreen.THINKING }
                    )
                    NavigationScreen.RISK -> RiskCalculatorScreen(
                        viewModel = viewModel
                    )
                    NavigationScreen.HISTORY -> TradeHistoryScreen(
                        viewModel = viewModel
                    )
                    NavigationScreen.TIMELINE -> DevelopmentTimelineScreen()
                }
            }
        }
    }
}
