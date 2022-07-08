package com.fab.home.data.repository.pokemon

import com.fab.home.core.Result
import com.fab.home.data.model.request.local.LocalDataSource
import com.fab.home.data.model.request.remote.RemoteDataSource
import com.fab.home.data.model.response.AbilityList
import com.fab.home.data.model.response.EvolutionChain
import com.fab.home.data.model.response.Pokemon
import com.fab.home.data.model.response.Specie

class PokemonRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : PokemonRepository {

    override suspend fun getPokemonList(): Result<List<Pokemon>> {
        val pokemonList = localDataSource.getPokemonList()
        return if (pokemonList.isNullOrEmpty()) {
            remoteDataSource.getPokemonList().results.forEach { pokemon ->
                localDataSource.savePokemon(pokemon)
            }
            return Result.Success(localDataSource.getPokemonList())
        } else {
            return Result.Success(pokemonList)
        }
    }

    override suspend fun getPokemonSpecies(name: String): Result<Specie> =
        remoteDataSource.getPokemonSpecies(name)

    override suspend fun getEvolutionChain(url: String): Result<EvolutionChain> {
        return Result.Success(remoteDataSource.getEvolutionChain(url).apply {
            val evoChainList = mutableListOf<String>()
            var evolutionData = this.chain;
            var isValid = true
            do {
                evoChainList.add(evolutionData.species.name)
                if (evolutionData.evolves_to.isNotEmpty())
                    evolutionData = evolutionData.evolves_to[0]
                else isValid = false
            } while (isValid)
            evolutionList = evoChainList
        })
    }

    override suspend fun getAbilityList(name: String): Result<AbilityList> =
        remoteDataSource.getAbilityList(name)
}