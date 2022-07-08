package com.fab.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fab.home.core.BaseViewHolder
import com.fab.home.data.model.response.Ability
import com.fab.home.databinding.PokemonItemBinding

class AbilityAdapter(
    private val abilityList: List<Ability>
) : RecyclerView.Adapter<BaseViewHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = AbilityViewHolder(itemBinding)
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is AbilityViewHolder -> holder.bind(abilityList[position])
        }
    }

    override fun getItemCount(): Int {
        return abilityList.size
    }

    private inner class AbilityViewHolder(val binding: PokemonItemBinding) :
        BaseViewHolder<Ability>(binding.root) {
        override fun bind(item: Ability) {
            binding.tvPokemonName.text = item.ability.name
        }
    }
}