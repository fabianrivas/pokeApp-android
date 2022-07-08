package com.fab.home.ui.abilitylist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.fab.home.R
import com.fab.home.adapter.AbilityAdapter
import com.fab.home.core.Result
import com.fab.home.data.model.request.local.LocalDataSource
import com.fab.home.data.model.request.remote.RemoteDataSource
import com.fab.home.data.repository.pokemon.PokemonRepositoryImpl
import com.fab.home.data.service.RetrofitClient
import com.fab.home.data.service.local.AppDatabase
import com.fab.home.databinding.FragmentAbilityListBinding
import com.fab.home.viewmodel.PokemonViewModel
import com.fab.home.viewmodel.PokemonViewModelFactory
import com.kennyc.view.MultiStateView


class AbilityListFragment : Fragment(R.layout.fragment_ability_list) {

    private val args by navArgs<AbilityListFragmentArgs>()
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
        val binding = FragmentAbilityListBinding.bind(view)
        args.let { fragmentargs ->
            fragmentargs.name.let { name ->
                viewModel.fetchAbilityList(name).observe(viewLifecycleOwner, Observer { result ->
                    when (result) {
                        is Result.Loading ->
                            binding.multiStateView.viewState = MultiStateView.ViewState.LOADING
                        is Result.Success -> {
                            binding.rvAbilityList.adapter = AbilityAdapter(result.data.abilities)
                            binding.multiStateView.viewState = MultiStateView.ViewState.CONTENT
                        }
                        is Result.Failure -> {
                            binding.multiStateView.viewState = MultiStateView.ViewState.ERROR
                        }
                    }
                })
            }

        }
    }
}