package com.fab.home.ui.pokemonlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fab.home.R
import com.fab.home.adapter.PokemonListAdapter
import com.fab.home.core.Result
import com.fab.home.data.model.request.local.LocalDataSource
import com.fab.home.data.model.request.remote.RemoteDataSource
import com.fab.home.data.repository.pokemon.PokemonRepositoryImpl
import com.fab.home.data.service.RetrofitClient
import com.fab.home.data.service.local.AppDatabase
import com.fab.home.databinding.FragmentPokemonListBinding
import com.fab.home.viewmodel.PokemonViewModel
import com.fab.home.viewmodel.PokemonViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.kennyc.view.MultiStateView

class PokemonListFragment : Fragment(R.layout.fragment_pokemon_list),
    PokemonListAdapter.OnPokemonClickListener {

    private lateinit var binding: FragmentPokemonListBinding
    private val viewModel by viewModels<PokemonViewModel> {
        PokemonViewModelFactory(
            PokemonRepositoryImpl(
                RemoteDataSource(RetrofitClient.webService),
                LocalDataSource(AppDatabase.getDatabase(requireContext()).pokemonDao())
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPokemonListBinding.bind(view)

        viewModel.fetchPokemonList().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.multiStateView.viewState = MultiStateView.ViewState.LOADING
                }
                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        binding.multiStateView.viewState = MultiStateView.ViewState.EMPTY
                        return@Observer
                    }
                    binding.rvPokemonList.adapter = PokemonListAdapter(result.data, this)
                    binding.multiStateView.viewState = MultiStateView.ViewState.CONTENT
                }
                is Result.Failure -> {
                    binding.multiStateView.viewState = MultiStateView.ViewState.ERROR
                }
            }
        })
    }

    override fun onPokemonClickListener(name: String) {
        findNavController().navigate(
            PokemonListFragmentDirections.actionPokemonListFragmentToPokemonSpecieFragment(name)
        )
    }
}