package com.fab.home.data.repository.fake

import com.fab.home.core.Result

interface FakeRepository {

    suspend fun saveFavorite(name: String): Result<String>
}