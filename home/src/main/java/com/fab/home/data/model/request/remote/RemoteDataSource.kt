package com.fab.home.data.model.request.remote

import com.fab.home.core.AppConstants
import com.fab.home.core.Result
import com.fab.home.data.model.response.AbilityList
import com.fab.home.data.model.response.EvolutionChain
import com.fab.home.data.model.response.PokemonResults
import com.fab.home.data.model.response.Specie
import com.fab.home.data.service.WebService

class RemoteDataSource(private val webService: WebService) {

    suspend fun getPokemonList(): PokemonResults =
        webService.getPokemonList(AppConstants.LIMIT)

    suspend fun getPokemonSpecies(name: String): Result<Specie> =
        Result.Success(webService.getPokemonSpecies(name))


    suspend fun getEvolutionChain(url: String): EvolutionChain =
        webService.getEvolutionChain(url)

    suspend fun getAbilityList(name: String): Result<AbilityList> =
        Result.Success(webService.getAbilityList(name))

}