package com.jorcollmar.rickandmorty.ui.episode

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.network.HttpException
import com.jorcollmar.rickandmorty.R
import com.jorcollmar.rickandmorty.domain.model.Episode
import com.jorcollmar.rickandmorty.ui.theme.RickAndMortyTheme
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodesListScreen(
    pagingItems: LazyPagingItems<Episode>,
    onEpisodeClicked: (String, List<Int>) -> Unit,
    onPullToRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val snackBarHostState = remember { SnackbarHostState() }
    val snackBarRetryText = stringResource(R.string.episodes_list_retry)
    val snackBarHttpErrorText = stringResource(R.string.episodes_list_http_error)
    val snackBarUnknownErrorText = stringResource(R.string.episodes_list_unknown_error)

    LaunchedEffect(key1 = pagingItems.loadState) {
        if (pagingItems.loadState.refresh is LoadState.Error) {
            val errorMessage = when ((pagingItems.loadState.refresh as LoadState.Error).error) {
                is HttpException -> snackBarHttpErrorText
                else -> snackBarUnknownErrorText
            }
            val result = snackBarHostState.showSnackbar(
                message = errorMessage,
                actionLabel = snackBarRetryText
            )
            when (result) {
                SnackbarResult.ActionPerformed -> {
                    onPullToRefresh()
                }

                SnackbarResult.Dismissed -> {
                    /* dismissed, no action needed */
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = pagingItems.loadState.refresh is LoadState.Loading,
            onRefresh = { onPullToRefresh() },
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    count = pagingItems.itemCount,
                    key = pagingItems.itemKey { it.id }
                ) { index ->
                    pagingItems[index]?.let {
                        EpisodeItem(
                            episode = it,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                                .clickable(
                                    onClick = {
                                        onEpisodeClicked(it.name, it.characters)
                                    }
                                )
                        )
                    }
                }
                item {
                    if (pagingItems.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                    if (pagingItems.loadState.append.endOfPaginationReached) {
                        EndWarning()
                    }
                }
            }
        }
    }
}

@Composable
fun EndWarning() {
    Card(
        modifier = Modifier.padding(horizontal = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Text(
            text = stringResource(R.string.episodes_list_no_more_episodes),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun EpisodesListPreview() {
    val fakeData = listOf(
        Episode(1, "Pilot", LocalDate.now(), "S01E01", listOf(1, 2)),
        Episode(2, "Test 1", LocalDate.now(), "S01E02", listOf(1, 2)),
        Episode(3, "Test 2", LocalDate.now(), "S01E03", listOf(1, 2)),
    )
    val pagingData = PagingData.from(fakeData)
    val fakeDataFlow = MutableStateFlow(pagingData)
    RickAndMortyTheme {

        EpisodesListScreen(
            pagingItems = fakeDataFlow.collectAsLazyPagingItems(),
            onEpisodeClicked = { _, _ -> },
            onPullToRefresh = {}
        )
    }
}

@Preview
@Composable
fun EndWarningPreview() {
    RickAndMortyTheme {
        EndWarning()
    }
}