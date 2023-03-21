package com.sberkozd.europa.ui.home

import com.sberkozd.europa.data.models.Country
import com.sberkozd.europa.di.NetworkRepository
import com.sberkozd.europa.di.NetworkResult
import com.sberkozd.europa.network.EuropaService
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HomeRepository @Inject constructor(private val europaService: EuropaService) :
    NetworkRepository {

    fun getCountryList(
        onError: suspend (String?) -> Unit,
    ) = flow {

        val result: NetworkResult<List<Country>> = wrapNetworkResult({
            europaService.getList()
        })

        when (result) {
            is NetworkResult.SuccessfulNetworkResult -> {
                emit(result.body)
            }
            is NetworkResult.ErrorNetworkResult -> {
                onError("Check internet connection")
            }
            is NetworkResult.EmptyNetworkResult -> {
                onError("No data found")
            }
            is NetworkResult.ExceptionResult -> {
                onError(result.errorMessage)
            }
        }
    }.flowOn(Dispatchers.IO)
}