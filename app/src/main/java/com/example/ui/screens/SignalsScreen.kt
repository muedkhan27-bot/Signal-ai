package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AssetClass
import com.example.ui.components.PlatformSelectorDialog
import com.example.ui.components.TradingSignalCard
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.EmeraldBull
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.RadiantGold
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignalsScreen(
    viewModel: MainViewModel,
    onNavigateToThinking: () -> Unit,
    modifier: Modifier = Modifier
) {
    val platform by viewModel.selectedPlatform.collectAsState()
    val signals by viewModel.signals.collectAsState()
    val selectedAssetClass by viewModel.selectedAssetClass.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val onlyTournament by viewModel.onlyTournamentVerified.collectAsState()

    var showPlatformDialog by remember { mutableStateOf(false) }

    val filteredSignals = signals.filter { sig ->
        val matchesAsset = selectedAssetClass == null || sig.assetClass == selectedAssetClass
        val matchesSearch = searchQuery.isBlank() ||
                sig.symbol.contains(searchQuery, ignoreCase = true) ||
                sig.name.contains(searchQuery, ignoreCase = true)
        val matchesTournament = !onlyTournament || sig.isTournamentVerified
        matchesAsset && matchesSearch && matchesTournament
    }

    if (showPlatformDialog) {
        PlatformSelectorDialog(
            currentPlatform = platform,
            onSelectPlatform = { viewModel.selectPlatform(it) },
            onDismiss = { showPlatformDialog = false }
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 14.dp)
            .testTag("signals_screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            // Active Platform Tailoring Banner
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkSurfaceElevated, RoundedCornerShape(12.dp))
                    .border(1.dp, NeonCyan.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                    .clickable { showPlatformDialog = true }
                    .padding(horizontal = 12.dp, vertical = 10.dp)
                    .testTag("active_platform_banner"),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(EmeraldBull, CircleShape)
                    )
                    Column {
                        Text(
                            text = "INTEGRATED PLATFORM",
                            color = TextTertiary,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = platform.displayName,
                            color = NeonCyan,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "CHANGE",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = "Change Platform",
                        tint = NeonCyan,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_ticker_field"),
                placeholder = {
                    Text("Search symbol (BTC, EUR/USD, NVDA, Gold)...", color = TextSecondary, fontSize = 12.sp)
                },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = NeonCyan, modifier = Modifier.size(18.dp))
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = TextSecondary,
                            modifier = Modifier
                                .size(18.dp)
                                .clickable { viewModel.setSearchQuery("") }
                        )
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = DarkSurfaceCard,
                    unfocusedContainerColor = DarkSurfaceCard,
                    focusedBorderColor = NeonCyan,
                    unfocusedBorderColor = DarkBorder,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                )
            )
        }

        // Filter Chips Row (All, Crypto, Forex, Stocks, Commodities, Indices, Tournament Verified)
        item {
            val scrollState = rememberScrollState()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // "All" chip
                FilterChip(
                    label = "All Markets",
                    isSelected = selectedAssetClass == null && !onlyTournament,
                    onClick = {
                        viewModel.selectAssetClassFilter(null)
                    }
                )

                // Tournament Verified Chip
                FilterChip(
                    label = "Tournament Top Picks",
                    icon = Icons.Default.EmojiEvents,
                    isSelected = onlyTournament,
                    highlightColor = RadiantGold,
                    onClick = { viewModel.toggleTournamentFilter() }
                )

                // Asset Class Chips
                AssetClass.entries.forEach { asset ->
                    FilterChip(
                        label = asset.displayName,
                        isSelected = selectedAssetClass == asset && !onlyTournament,
                        onClick = { viewModel.selectAssetClassFilter(asset) }
                    )
                }
            }
        }

        // Section header with signal count
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${filteredSignals.size} ACTIVE REAL-TIME SIGNALS",
                    color = TextSecondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Text(
                    text = "Auto-Synced (100% Platform Precision)",
                    color = EmeraldBull,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // List of Signals
        items(filteredSignals, key = { it.id }) { signal ->
            TradingSignalCard(
                signal = signal,
                platform = platform,
                onCopyOrder = { viewModel.copyOrderTicket(signal) },
                onDeepThinkingClick = {
                    viewModel.inspectSignal(signal)
                    viewModel.requestHighThinkingAnalysis(signal)
                    onNavigateToThinking()
                },
                onTournamentExecute = {
                    viewModel.executeSignalToTournament(signal)
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun FilterChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    highlightColor: Color = NeonCyan
) {
    Box(
        modifier = Modifier
            .background(
                if (isSelected) highlightColor.copy(alpha = 0.2f) else DarkSurfaceCard,
                RoundedCornerShape(20.dp)
            )
            .border(
                1.dp,
                if (isSelected) highlightColor else DarkBorder,
                RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) highlightColor else TextSecondary,
                    modifier = Modifier.size(13.dp)
                )
            }
            Text(
                text = label,
                color = if (isSelected) TextPrimary else TextSecondary,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            )
        }
    }
}
