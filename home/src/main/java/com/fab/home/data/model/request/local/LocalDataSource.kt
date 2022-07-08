package com.fab.home.data.model.request.local

import com.fab.home.data.model.response.Pokemon

class LocalDataSource(private val pokemonDao: PokemonDao) {

    suspend fun getPokemonList(): List<Pokemon> = pokemonDao.getPokemonList()

    suspend fun savePokemon(pokemon: Pokemon) {
        pokemonDao.savePokemon(pokemon)
    }
}