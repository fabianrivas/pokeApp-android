package com.fab.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.fab.home.core.Result
import com.fab.home.data.repository.pokemon.PokemonRepository
import kotlinx.coroutines.Dispatchers

class PokemonViewModel(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    fun fetchPokemonList() =
        liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
            emit(Result.Loading())
            kotlin.runCatching {
                pokemonRepository.getPokemonList()
            }.onSuccess { result ->
                emit(result)
            }.onFailure { throwable ->
                emit(Result.Failure(Exception(throwable.message)))
            }
        }

    fun fetchPokemonSpecie(name: String) =
        liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
            emit(Result.Loading())
            kotlin.runCatching {
                pokemonRepository.getPokemonSpecies(name)
            }.onSuccess { result ->
                emit(result)
            }.onFailure { throwable ->
                emit(Result.Failure(Exception(throwable.message)))
            }
        }

    fun fetchEvolutionChain(url: String) =
        liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
            emit(Result.Loading())
            kotlin.runCatching {
                pokemonRepository.getEvolutionChain(url)
            }.onSuccess { result ->
                emit((result))
            }.onFailure { throwable ->
                emit(Result.Failure(Exception(throwable.message)))
            }
        }

    fun fetchAbilityList(name: String) =
        liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
            emit(Result.Loading())
            kotlin.runCatching {
                pokemonRepository.getAbilityList(name)
            }.onSuccess { result ->
                emit(result)
            }.onFailure { throwable ->
                emit(Result.Failure(Exception(throwable.message)))
            }
        }
}

class PokemonViewModelFactory(
    private val repo: PokemonRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PokemonRepository::class.java).newInstance(repo)
    }
}