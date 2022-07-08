package com.fab.home.ui.evolutionchain

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fab.home.R
import com.fab.home.adapter.EvolutionChainAdapter
import com.fab.home.core.Result
import com.fab.home.data.model.request.fake.FakeDataSource
import com.fab.home.data.model.request.local.LocalDataSource
import com.fab.home.data.model.request.remote.RemoteDataSource
import com.fab.home.data.repository.fake.FakeRepositoryImpl
import com.fab.home.data.repository.pokemon.PokemonRepositoryImpl
import com.fab.home.data.service.RetrofitClient
import com.fab.home.data.service.local.AppDatabase
import com.fab.home.databinding.FragmentEvolutionChainBinding
import com.fab.home.viewmodel.PokemonViewModel
import com.fab.home.viewmodel.PokemonViewModelFactory
import com.fab.home.viewmodel.fake.FakeViewModel
import com.fab.home.viewmodel.fake.FakeViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.kennyc.view.MultiStateView


class EvolutionChainFragment : Fragment(R.layout.fragment_evolution_chain),
    EvolutionChainAdapter.OnEvolutionClickListener {

    private lateinit var binding: FragmentEvolutionChainBinding
    private val args by navArgs<EvolutionChainFragmentArgs>()

    private val viewModel by viewModels<PokemonViewModel> {
        PokemonViewModelFactory(
            PokemonRepositoryImpl(
                RemoteDataSource(RetrofitClient.webService),
                LocalDataSource(AppDatabase.getDatabase(requireContext()).pokemonDao())
            )
        )
    }
    private val viewModelFake by viewModels<FakeViewModel> {
        FakeViewModelFactory(FakeRepositoryImpl(FakeDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEvolutionChainBinding.bind(view)

        args.let {
            viewModel.fetchEvolutionChain(it.url).observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.multiStateView.viewState = MultiStateView.ViewState.LOADING
                    }
                    is Result.Success -> {
                        binding.rvEvolutionList.adapter =
                            EvolutionChainAdapter(result.data.evolutionList, this)
                        binding.multiStateView.viewState = MultiStateView.ViewState.CONTENT
                    }
                    is Result.Failure -> {
                        binding.multiStateView.viewState = MultiStateView.ViewState.ERROR
                    }
                }
            })
        }
    }

    override fun onEvolutionClickListener(name: String) {
        viewModelFake.saveFavorite(name).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    goToHome(result.data)
                }
                is Result.Failure -> {
                    goToHome(result.exception.message!!)
                }
            }
        })
    }

    private fun goToHome(message: String) {
        findNavController().navigate(
            EvolutionChainFragmentDirections.actionEvolutionChainFragmentToPokemonListFragment()
        )
        showMessage(message)
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_LONG
        ).setDuration(5000).show()
    }

}