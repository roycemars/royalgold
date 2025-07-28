package com.roycemars.royalgold.feature.market.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketScreen(
    viewModel: CryptoViewModel = hiltViewModel()
) {
    val cryptoListings = viewModel.cryptoPagingFlow.collectAsLazyPagingItems()

    val context = LocalContext.current

    // LaunchedEffect for logging snapshot (can be kept or removed for production)
    LaunchedEffect(cryptoListings.itemSnapshotList) {
        val currentItemsSnapshot = cryptoListings.itemSnapshotList
        for (item in currentItemsSnapshot) {
            Log.d(
                "MarketScreenDebug",
                "Snapshot Item: ${item?.symbol}, Price: ${item?.price}, 1h: ${item?.percentChange1h}"
            )
        }
    }

    // LaunchedEffect for handling Paging load state errors
    LaunchedEffect(key1 = cryptoListings.loadState) {
        val refreshState = cryptoListings.loadState.refresh
        if (refreshState is LoadState.Error) {
            Toast.makeText(
                context,
                "Error loading data: " + refreshState.error.localizedMessage,
                Toast.LENGTH_LONG
            ).show()
        }

        val appendState = cryptoListings.loadState.append
        if (appendState is LoadState.Error) {
            Toast.makeText(
                context,
                "Error loading more items: " + appendState.error.localizedMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Box(modifier = Modifier.weight(1f)) {
            // Initial loading state for the list
            if (cryptoListings.loadState.refresh is LoadState.Loading && cryptoListings.itemCount == 0) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (cryptoListings.loadState.refresh is LoadState.Error && cryptoListings.itemCount == 0) {
                // Initial load error state
                Text(
                    "Failed to load market data. Please try again later.",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp), // Adjusted spacing slightly
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                ) {
                    // Optional: Sticky Header
//                     stickyHeader {
//                         HeaderCard(modifier = Modifier.fillMaxWidth()) // Your custom header composable
//                     }

                    // Displaying the PagingData items
                    items(
                        count = cryptoListings.itemCount,
                        // Optional: key for item identity and animations, if your Crypto object has a stable ID
                        key = cryptoListings.itemKey { crypto -> crypto.id }
                    ) { index ->
                        val crypto = cryptoListings[index] // Get the item at the current index
                        if (crypto != null) {
                            CryptoItem(
                                crypto = crypto,
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else {
                            // Placeholder for null items, though Paging 3 usually compacts nulls
                            // You might want a shimmer/loading placeholder here if needed
                            // For now, let's just log it or show a simple text
                            Log.d(
                                "MarketScreen",
                                "Crypto item at index $index is null (placeholder state)."
                            )
                            // Spacer(modifier = Modifier.fillMaxWidth().height(50.dp).background(Color.LightGray)) // Example placeholder
                        }
                    }

                    // Handle loading state for appending more items (pagination loading)
                    cryptoListings.loadState.append.let { loadState ->
                        when (loadState) {
                            is LoadState.Loading -> {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }

                            is LoadState.Error -> {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            "Error loading more. ${loadState.error.localizedMessage}",
                                            color = MaterialTheme.colorScheme.error
                                        )
                                        // You could add a retry button here
                                    }
                                }
                            }

                            is LoadState.NotLoading -> {
                                if (loadState.endOfPaginationReached && cryptoListings.itemCount == 0) {
                                    item {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text("No cryptocurrencies found.")
                                        }
                                    }
                                } else if (loadState.endOfPaginationReached) {
                                    item {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text("You've reached the end of the list.")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}