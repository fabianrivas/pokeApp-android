package com.fab.home.data.repository.fake

import com.fab.home.core.Result
import com.fab.home.data.model.request.fake.FakeDataSource

class FakeRepositoryImpl(
    private val fakeDataSource: FakeDataSource
) : FakeRepository {

    override suspend fun saveFavorite(name: String): Result<String> =
        fakeDataSource.saveFavorite(name)
}