package com.jorcollmar.rickandmorty.ui.episode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorcollmar.rickandmorty.R
import com.jorcollmar.rickandmorty.domain.model.Episode
import com.jorcollmar.rickandmorty.ui.theme.RickAndMortyTheme
import java.time.format.DateTimeFormatter

private const val AIR_DATE_FORMAT = "dd/MM/yyyy"

@Composable
fun EpisodeItem(
    episode: Episode,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            val title by remember { mutableStateOf("${episode.code} - ${episode.name}") }
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = episode.airDate?.format(
                    DateTimeFormatter.ofPattern(AIR_DATE_FORMAT)
                ) ?: stringResource(R.string.episodes_list_air_date_not_available),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Preview
@Composable
fun EpisodeItemPreview() {
    RickAndMortyTheme {
        EpisodeItem(
            episode = Episode(
                id = 1,
                name = "Pilot",
                airDate = null,
                code = "S01E01",
                characters = listOf(1, 2)
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}