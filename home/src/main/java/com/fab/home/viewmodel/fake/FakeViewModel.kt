package com.fab.home.viewmodel.fake

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.fab.home.core.Result
import com.fab.home.data.repository.fake.FakeRepository
import kotlinx.coroutines.Dispatchers

class FakeViewModel(
    private val fakeRepository: FakeRepository
) : ViewModel() {

    fun saveFavorite(name: String) =
        liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
            kotlin.runCatching {
                fakeRepository.saveFavorite(name)
            }.onSuccess { result ->
                emit(result)
            }.onFailure { throwable ->
                emit(Result.Failure(Exception(throwable.message)))
            }
        }
}

class FakeViewModelFactory(
    private val repo: FakeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(FakeRepository::class.java).newInstance(repo)
    }
}