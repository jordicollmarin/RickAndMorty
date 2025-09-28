# Rick and Morty!
> Check all about Rick and Morty TV animation cartoon series.

## Table of contents
* [General info](#general-info)
* [Features](#features)
* [Technologies](#technologies)
* [Architecture](#architecture)
* [Future](#future)
* [Installation](#installation)
* [Configuration](#configuration)
* [Build](#build)
* [Status](#status)
* [Contact](#contact)

## General info
RickAndMorty is an app that will help users to get information about Rick and Morty TV animation cartoon series. You will be able to see a list of Episodes and the characters details of each episode. The app is using the public API https://rickandmortyapi.com/documentation/#rest.

## Features
The Mobile App is easy to use and enables users to:
* See the full list of episodes of Rick and Morty showing the `name`, `air date` and the `code of the episode`. Once all the episodes are loaded, a message will be shown.
* Get the list of characters of an episode when tapping on it.
* See the details of each character when tapping on it. The information shown is an `image`, the `name`, the `status`, the `species`, the `name of the origin` and the `total number of episodes` the character appears in.
* Export the details of a character when tapping on the share icon in the `TopAppBar` of the Character details screen.
* `Pull to refresh` is implemented to refresh the list of Episodes.
* Rotation of the device is supported.

## Technologies
* Jetpack Compose
* Jetpack Compose Navigation
* Paging3
* Room
* Hilt
* Coil
* Mockk

## Architecture
* Clean architecture (Repository approach).
* Persistence mechanism implemented using `Room`.
* MVI.

## Future
* Share the details of each characters by exporting it in a .txt file.
* Add a timestamp to show the last time the list was refreshed.
* Add unit tests for `EpisodeRepository`.
* Add unit tests for `RickAndMortyViewModel`.
* Add unit tests for `RickAndMortyRemoteMediator`.
* Add a UI test using `testTag` from Compose to check that the list of Episodes is shown when loading the app.
* Add a UI tests using `testTag` from Compose to check that the Episode details is shown properly (`EpisodeItem`).
* Add a UI test using `testTag` from Compose to check that the 'end of episodes' message is shown at the end of the list of Episodes when all have been loaded.
* Add a UI tests using `testTag` from Compose to check the error and loading states for the `EpisodesListScreen` and `CharacterDetailsScreen`.
* Add a UI test using `testTag` from Compose to check that the Character information shows when loading the `CharacterDetailsScreen`.

## Installation
Clone this repository and import into **Android Studio**
```bash
git clone git@github.com:jordicollmarin/RickAndMorty.git
```

## Configuration
The app needs no configuration. Once it is imported, you should be able to execute it and use it.

## Build
Build the project in Debug mode like this:
`./gradlew buildDebug`

## Status
Project is: _in progress_.

## Contact
Jordi Coll Marin
* Email: jorcollmar@gmail.com
* LinkedIn profile: https://www.linkedin.com/in/jordi-coll-marin/
