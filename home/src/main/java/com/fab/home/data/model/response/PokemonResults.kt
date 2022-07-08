package com.fab.home.data.model.response

data class PokemonResults(
    val count: Int,
    val next: String,
    val results: List<Pokemon>
)
