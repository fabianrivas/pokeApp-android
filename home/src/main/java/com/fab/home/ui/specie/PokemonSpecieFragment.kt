package com.fab.home.ui.specie

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fab.home.R
import com.fab.home.core.Result
import com.fab.home.data.model.request.local.LocalDataSource
import com.fab.home.data.model.request.remote.RemoteDataSource
import com.fab.home.data.model.response.Specie
import com.fab.home.data.repository.pokemon.PokemonRepositoryImpl
import com.fab.home.data.service.RetrofitClient
import com.fab.home.data.service.local.AppDatabase
import com.fab.home.databinding.FragmentPokemonSpecieBinding
import com.fab.home.viewmodel.PokemonViewModel
import com.fab.home.viewmodel.PokemonViewModelFactory
import com.kennyc.view.MultiStateView


class PokemonSpecieFragment : Fragment(R.layout.fragment_pokemon_specie) {

    private lateinit var binding: FragmentPokemonSpecieBinding
    private lateinit var evolutionURL: String
    private val args by navArgs<PokemonSpecieFragmentArgs>()
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
        binding = FragmentPokemonSpecieBinding.bind(view)
        setupButtonClickListener()
        args.let {
            getPokemonSpecie(it.name)
        }
    }

    private fun setupButtonClickListener() {
        args.let { args ->
            binding.btnEvolutionChain.setOnClickListener {
                findNavController().navigate(
                    PokemonSpecieFragmentDirections.actionPokemonSpecieFragmentToEvolutionChainFragment(
                        evolutionURL
                    )
                )
            }
            binding.btnAbilities.setOnClickListener {
                findNavController().navigate(
                    PokemonSpecieFragmentDirections.actionPokemonSpecieFragmentToAbilityListFragment(
                        args.name
                    )
                )
            }
        }
    }

    private fun getPokemonSpecie(name: String) {
        viewModel.fetchPokemonSpecie(name).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.multiStateView.viewState = MultiStateView.ViewState.LOADING
                }
                is Result.Success -> {
                    setupView(result.data)
                }
                is Result.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        result.exception.toString(),
                        Toast.LENGTH_SHORT
                    )
                    binding.multiStateView.viewState = MultiStateView.ViewState.ERROR
                }
            }
        })
    }

    private fun setupView(specie: Specie) {
        evolutionURL = specie.evolutionChain.url
        binding.name.text = specie.name
        binding.tvBaseHappiness.text = specie.baseHappiness.toString()
        binding.tvCaptureRate.text = specie.captureRate.toString()
        binding.tvColor.text = specie.color.name
        binding.tvEggGroups.text = specie.eggGroups.joinToString { "${it.name}" }
        binding.multiStateView.viewState = MultiStateView.ViewState.CONTENT
    }
}