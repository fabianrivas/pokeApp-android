package com.fab.home.data.model.request.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fab.home.data.model.response.Pokemon

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePokemon(pokemon: Pokemon)

    @Query("SELECT * FROM pokemon")
    suspend fun getPokemonList(): List<Pokemon>
}