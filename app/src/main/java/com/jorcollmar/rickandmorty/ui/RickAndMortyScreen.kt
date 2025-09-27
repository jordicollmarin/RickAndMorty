package com.jorcollmar.rickandmorty.ui

import android.content.ContentResolver
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.jorcollmar.rickandmorty.RickAndMortyViewModel
import com.jorcollmar.rickandmorty.domain.model.Character
import com.jorcollmar.rickandmorty.ui.character.CharacterDetailsScreen
import com.jorcollmar.rickandmorty.ui.character.CharactersListScreen
import com.jorcollmar.rickandmorty.ui.episode.EpisodesListScreen
import java.io.FileOutputStream

const val TEXT_PLAIN_MIME_TYPE = "text/plain"

@Composable
fun RickAndMortyScreen(
    viewModel: RickAndMortyViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val episodes = viewModel.episodes.collectAsLazyPagingItems()

    NavHost(
        navController = navController,
        startDestination = RickAndMortyRouter.EpisodesList.name,
    ) {
        composable(
            route = RickAndMortyRouter.EpisodesList.name
        ) {
            EpisodesListScreen(
                pagingItems = episodes,
                onEpisodeClicked = { episodeName, characterIds ->
                    navController.navigate(
                        route = RickAndMortyRouter.CharactersList(episodeName, characterIds)
                    )
                },
                onPullToRefresh = { episodes.refresh() }
            )
        }
        composable<RickAndMortyRouter.CharactersList> {
            val charactersList = it.toRoute<RickAndMortyRouter.CharactersList>()
            CharactersListScreen(
                episodeName = charactersList.episodeName,
                characterIds = charactersList.characterIds,
                onCharacterClicked = { characterId ->
                    navController.navigate(
                        route = RickAndMortyRouter.CharacterDetails(characterId)
                    )
                },
                onBackPressed = { navController.navigateUp() }
            )
        }
        composable<RickAndMortyRouter.CharacterDetails> { navBackStackEntry ->
            val characterDetails = navBackStackEntry.toRoute<RickAndMortyRouter.CharacterDetails>()
            var characterInfo by remember { mutableStateOf<Character?>(null) }
            val contentResolver = LocalContext.current.contentResolver
            val exportCharacterLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.CreateDocument(mimeType = TEXT_PLAIN_MIME_TYPE)
            ) {
                writeCharacterInSelectedFile(
                    uri = it,
                    contentResolver = contentResolver,
                    characterInfo = characterInfo,
                    onError = viewModel::showExportCharacterError
                )
            }
            CharacterDetailsScreen(
                characterId = characterDetails.characterId,
                onExportCharacterClick = { character ->
                    characterInfo = character
                    val fileName = "${character.name}.txt"
                    exportCharacterLauncher.launch(fileName)
                },
                onBackPressed = { navController.navigateUp() }
            )
        }
    }
}

private fun writeCharacterInSelectedFile(
    uri: Uri?,
    contentResolver: ContentResolver,
    characterInfo: Character?,
    onError: () -> Unit
) {
    uri?.let { uri ->
        try {
            contentResolver.openFileDescriptor(uri, "w")?.use {
                FileOutputStream(it.fileDescriptor).use { fos ->
                    fos.write(characterInfo.toString().toByteArray())
                }
            }
        } catch (e: Exception) {
            onError()
        }
    }
}