package com.jorcollmar.rickandmorty.ui.episode

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.jorcollmar.rickandmorty.R
import com.jorcollmar.rickandmorty.domain.model.Episode
import com.jorcollmar.rickandmorty.ui.theme.RickAndMortyTheme
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate

@Composable
fun EpisodesListScreen(
    episodes: LazyPagingItems<Episode>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = episodes.loadState) {
        // TODO Architecture - Show custom error
        if (episodes.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (episodes.loadState.refresh as LoadState.Error).error.localizedMessage,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (episodes.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    count = episodes.itemCount,
                    key = episodes.itemKey { it.id }
                ) { index ->
                    episodes[index]?.let {
                        EpisodeItem(
                            episode = it,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                item {
                    if (episodes.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                    if (episodes.loadState.append.endOfPaginationReached) {
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
        colors = CardDefaults.cardColors(
            containerColor = Color.Yellow
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
        Episode(1, "Pilot", LocalDate.now(), "S01E01", listOf("Character 1", "Character 2")),
        Episode(2, "Test 1", LocalDate.now(), "S01E02", listOf("Character 1", "Character 2")),
        Episode(3, "Test 2", LocalDate.now(), "S01E03", listOf("Character 1", "Character 2")),
    )
    val pagingData = PagingData.from(fakeData)
    val fakeDataFlow = MutableStateFlow(pagingData)
    RickAndMortyTheme {

        EpisodesListScreen(
            episodes = fakeDataFlow.collectAsLazyPagingItems()
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