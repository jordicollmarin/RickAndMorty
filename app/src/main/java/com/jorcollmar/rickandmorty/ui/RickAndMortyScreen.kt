package com.jorcollmar.rickandmorty.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.jorcollmar.rickandmorty.RickAndMortyViewModel
import com.jorcollmar.rickandmorty.ui.character.CharacterDetailsScreen
import com.jorcollmar.rickandmorty.ui.character.CharactersListScreen
import com.jorcollmar.rickandmorty.ui.episode.EpisodesListScreen

@Composable
fun RickAndMortyScreen(
    viewModel: RickAndMortyViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val episodes = viewModel.episodes.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.uiEvents.collect { action ->
            when (action) {
                is RickAndMortyUiEvents.RefreshEpisodes -> episodes.refresh()

                is RickAndMortyUiEvents.SeeEpisodeDetails -> navController.navigate(
                    route = RickAndMortyRouter.CharactersList(
                        episodeName = action.episodeName,
                        characterIds = action.characterIds,
                    )
                )

                is RickAndMortyUiEvents.SeeCharacterDetails -> navController.navigate(
                    route = RickAndMortyRouter.CharacterDetails(action.characterId)
                )

                is RickAndMortyUiEvents.ExportCharacterDetails -> {
                    // TODO Export character into a txt file
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = RickAndMortyRouter.EpisodesList.name,
    ) {
        composable(
            route = RickAndMortyRouter.EpisodesList.name
        ) {
            EpisodesListScreen(
                pagingItems = episodes,
                onEpisodeClicked = viewModel::onEpisodeClicked,
                onPullToRefresh = viewModel::onPullToRefresh
            )
        }
        composable<RickAndMortyRouter.CharactersList> {
            val charactersList = it.toRoute<RickAndMortyRouter.CharactersList>()
            CharactersListScreen(
                episodeName = charactersList.episodeName,
                characterIds = charactersList.characterIds,
                onCharacterClicked = viewModel::onCharacterIdClicked,
                onBackPressed = { navController.navigateUp() }
            )
        }
        composable<RickAndMortyRouter.CharacterDetails> {
            val characterDetails = it.toRoute<RickAndMortyRouter.CharacterDetails>()
            CharacterDetailsScreen(
                characterId = characterDetails.characterId,
                onExportCharacterClick = viewModel::onExportCharacterClicked,
                onBackPressed = { navController.navigateUp() }
            )
        }
    }
}