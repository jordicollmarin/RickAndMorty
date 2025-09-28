package com.jorcollmar.rickandmorty.ui.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.jorcollmar.rickandmorty.R
import com.jorcollmar.rickandmorty.RickAndMortyViewModel
import com.jorcollmar.rickandmorty.domain.model.Character
import com.jorcollmar.rickandmorty.ui.theme.RickAndMortyTheme

private const val IMAGE_SIZE = 300
private const val INFORMATION_PADDING = 10

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CharacterDetailsScreen(
    characterId: Int,
    modifier: Modifier = Modifier,
    viewModel: RickAndMortyViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onExportCharacterClick: (Character) -> Unit,
) {
    viewModel.loadCharacterDetails(characterId)
    val snackBarHostState = remember { SnackbarHostState() }
    val snackBarErrorText = stringResource(R.string.character_details_export_error)
    val uiStateCharacter by viewModel.uiStateCharacter.collectAsStateWithLifecycle()

    when (uiStateCharacter) {
        is CharacterDetailsUiState.LoadingError -> Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.character_details_error)
                )
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = onBackPressed
                ) {
                    Text(
                        text = stringResource(R.string.back)
                    )
                }
            }
        }

        is CharacterDetailsUiState.ExportError -> {
            LaunchedEffect(Unit) {
                snackBarHostState.showSnackbar(message = snackBarErrorText)
            }
        }

        is CharacterDetailsUiState.Loading -> Box(
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        is CharacterDetailsUiState.Success -> {
            val character = (uiStateCharacter as CharacterDetailsUiState.Success).character

            Scaffold(
                snackbarHost = { SnackbarHost(snackBarHostState) },
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = { Text(character.name) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        ),
                        navigationIcon = {
                            IconButton(
                                onClick = { onBackPressed() }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = stringResource(R.string.back)
                                )
                            }
                        },
                        actions = {
                            IconButton(
                                onClick = {
                                    onExportCharacterClick(character)
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_share_24),
                                    contentDescription = stringResource(R.string.character_details_share_character_details)
                                )
                            }
                        }
                    )
                },
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CharacterImage(
                        characterImageUrl = character.imageUrl,
                        modifier = modifier.size(IMAGE_SIZE.dp, IMAGE_SIZE.dp)
                    )
                    Text(
                        modifier = Modifier.padding(vertical = INFORMATION_PADDING.dp),
                        text = character.status,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        modifier = Modifier.padding(vertical = INFORMATION_PADDING.dp),
                        text = character.species,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        modifier = Modifier.padding(vertical = INFORMATION_PADDING.dp),
                        text = character.originName,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        modifier = Modifier.padding(vertical = INFORMATION_PADDING.dp),
                        text = pluralStringResource(
                            R.plurals.character_details_episodes_count,
                            character.appearanceCount,
                            character.appearanceCount
                        ),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}

@Composable
fun CharacterImage(
    characterImageUrl: String,
    modifier: Modifier = Modifier
) {
    val imagePainter: AsyncImagePainter =
        rememberAsyncImagePainter(
            model =
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(characterImageUrl)
                    .build(),
        )
    Image(
        painter = imagePainter,
        contentScale = ContentScale.Crop,
        contentDescription = stringResource(id = R.string.character_details_image_content_description),
        modifier = modifier,
    )
}

@Preview
@Composable
fun EpisodesListPreview() {
    RickAndMortyTheme {
        CharacterDetailsScreen(
            characterId = 3,
            onBackPressed = {},
            onExportCharacterClick = {}
        )
    }
}