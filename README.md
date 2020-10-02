<h1 align="center">CoinTracker</h1>

<p align="center">
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
</p>

<p align="center">  
CoinTracker is a sample application getting data from Crypto Currency API - Coingecko via Retrofit and saving it locally via Room. Then it retrieves data from local database. Project is written in Kotlin by appling architecture pattern of MVVM. Dagger-Hilt is used for dependency injection..
</p>
</br>

<p align="center">
<img src="/previews/SS1.png" width="20%"/>
<img src="/previews/SS2.png" width="20%"/>
<img src="/previews/SS3.png" width="20%"/>
</p>
<p align="center">
<img src="/previews/SS4.png" width="20%"/>
<img src="/previews/SS5.png" width="20%"/>
<img src="/previews/SS6.png" width="20%"/>
</p>


## Tech stack & Open-source libraries

<img src="/previews/preview.gif" align="right" width="25%"/>

- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Dagger-Hilt (alpha) for dependency injection.
- JetPack
  - LiveData - notify domain layer data to views.
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct a database using the abstract layer.
  - WorkManager - handle background operations
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Glide](https://github.com/bumptech/glide) - loading images.

## Architecture
CoinTracker uses MVVM architecture and a repository pattern.

![architecture](https://user-images.githubusercontent.com/24237865/77502018-f7d36000-6e9c-11ea-92b0-1097240c8689.png)

## API

<img src="https://static.coingecko.com/s/coingecko-logo-d13d6bcceddbb003f146b33c2f7e8193d72b93bb343d38e392897c3df3e78bdd.png" align="right" width="21%"/>

CoinTracker uses the [Coin Gecko API](https://www.coingecko.com/en/api#explore-api) for constructing RESTful API.<br>
Coin Gecko API allows developers to access information about worldwide Crypto Coins.