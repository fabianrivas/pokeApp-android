package com.fab.home.data.model.response

import com.google.gson.annotations.SerializedName

data class Specie(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("base_happiness")
    val baseHappiness: Int,

    @SerializedName("capture_rate")
    val captureRate: Int,

    @SerializedName("color")
    val color: ItemBase,

    @SerializedName("egg_groups")
    val eggGroups: List<ItemBase>,

    @SerializedName("evolution_chain")
    val evolutionChain: ItemBase

)