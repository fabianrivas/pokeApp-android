package com.fab.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fab.home.core.BaseViewHolder
import com.fab.home.data.model.response.Pokemon
import com.fab.home.databinding.PokemonItemBinding

class PokemonListAdapter(
    private val pokemonList: List<Pokemon>,
    private val itemClickListener: OnPokemonClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {
    interface OnPokemonClickListener {
        fun onPokemonClickListener(name: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = PokemonListViewHolder(itemBinding)

        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onPokemonClickListener(pokemonList[position].name)
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is PokemonListViewHolder -> holder.bind(pokemonList[position])
        }
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    private inner class PokemonListViewHolder(val binding: PokemonItemBinding) :
        BaseViewHolder<Pokemon>(binding.root) {
        override fun bind(item: Pokemon) {
            binding.tvPokemonName.text = item.name
        }
    }
}