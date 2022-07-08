package com.fab.home.data.repository.pokemon

import com.fab.home.core.Result
import com.fab.home.data.model.response.*

interface PokemonRepository {

    suspend fun getPokemonList(): Result<List<Pokemon>>

    suspend fun getPokemonSpecies(name: String): Result<Specie>

    suspend fun getEvolutionChain(url: String): Result<EvolutionChain>

    suspend fun getAbilityList(name: String): Result<AbilityList>

}