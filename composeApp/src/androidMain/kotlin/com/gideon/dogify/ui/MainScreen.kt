package com.gideon.dogify.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.gideon.dogify.Event
import com.gideon.dogify.MainViewModel
import com.gideon.dogify.State
import com.gideon.dogify.model.Breed
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state by viewModel.state.collectAsState()
    val breeds by viewModel.breeds.collectAsState()
    val events by viewModel.events.collectAsState(Unit)
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val shouldFilterFavourites by viewModel.shouldFilterFavourites.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarCoroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = viewModel::refresh,
            modifier = Modifier.padding(paddingValues)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Row(
                    Modifier
                        .wrapContentWidth(Alignment.End)
                        .padding(8.dp)
                ) {
                    Text(text = "Filter favourites")
                    Switch(
                        checked = shouldFilterFavourites,
                        modifier = Modifier.padding(horizontal = 8.dp),
                        onCheckedChange = { viewModel.onToggleFavouriteFilter() }
                    )
                }
                when (state) {
                    State.LOADING -> {
                        Spacer(Modifier.weight(1f))
                        CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
                        Spacer(Modifier.weight(1f))
                    }
                    State.NORMAL -> Breeds(
                        breeds = breeds,
                        onFavouriteTapped = viewModel::onFavouriteTapped
                    )

                    State.ERROR -> {
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = "Oops something went wrong...",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(Modifier.weight(1f))
                    }
                    State.EMPTY -> {
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = "Oops looks like there are no ${if (shouldFilterFavourites) "favourites" else "dogs"}",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(Modifier.weight(1f))
                    }
                }
            }
        }

        // Handle error events
        LaunchedEffect(events) {
            if (events == Event.Error) {
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar("Oops something went wrong...")
            }
        }
    }
}


@Composable
fun Breeds(breeds: List<Breed>, onFavouriteTapped: (Breed) -> Unit = {}) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(breeds) {
            Column(Modifier.padding(8.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(model = it.imageUrl),
                    contentDescription = "${it.name}-image",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Crop
                )
                Row(Modifier.padding(vertical = 8.dp)) {
                    Text(
                        text = it.name,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(Modifier.weight(1f))
                    Icon(
                        if (it.isFavourite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Mark as favourite",
                        modifier = Modifier.clickable {
                            onFavouriteTapped(it)
                        }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun BreedsPreview() {
    MaterialTheme {
        Surface {
            Breeds(breeds = (0 until 10).map {
                Breed(
                    name = "Breed $it",
                    imageUrl = "",
                    isFavourite = it % 2 == 0
                )
            })
        }
    }
}