package com.fab.home.data.model.response

data class EvolutionChain(
    val baby_trigger_item: ItemBase?,
    val chain: ChainLink,
    val id: Int,
    var evolutionList: List<String>

)

data class ChainLink(
    val evolves_to: List<ChainLink>,
    val is_baby: Boolean,
    val species: ItemBase
)




