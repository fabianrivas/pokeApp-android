package com.fab.home.data.model.response


data class AbilityList(
    val abilities: List<Ability>,
)

data class Ability(
    val ability: ItemBase,
    val is_hidden: Boolean,
    val slot: Int
)



