package com.fab.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fab.home.core.BaseViewHolder
import com.fab.home.databinding.PokemonItemBinding

class EvolutionChainAdapter(
    private val evolutionList: List<String>,
    private val itemClickListener: OnEvolutionClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnEvolutionClickListener {
        fun onEvolutionClickListener(name: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = EvolutionChainViewHolder(itemBinding)
        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onEvolutionClickListener(evolutionList[position])
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is EvolutionChainViewHolder -> holder.bind(evolutionList[position])
        }
    }

    override fun getItemCount(): Int {
        return evolutionList.size
    }

    private inner class EvolutionChainViewHolder(val binding: PokemonItemBinding) :
        BaseViewHolder<String>(binding.root) {
        override fun bind(item: String) {
            binding.tvPokemonName.text = item
        }
    }
}