package com.fab.home.data.model.request.fake

import com.fab.home.core.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


class FakeDataSource {

    suspend fun saveFavorite(name: String): Result<String> {
        val responseList = listOf(true, false)
        val responseMessage: String
        val responseStatus: Boolean
        withContext(Dispatchers.IO) {
            responseStatus = responseList.random()
            delay(500)
            if (responseStatus) {
                responseMessage = "favorito - $name"
            } else {
                responseMessage = "error - $name"
            }
        }
        return if (responseStatus)
            Result.Success(responseMessage)
        else Result.Failure(Exception(responseMessage))
    }
}