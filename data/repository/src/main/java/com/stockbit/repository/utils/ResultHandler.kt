package com.stockbit.repository.utils

import com.stockbit.model.Result
import com.stockbit.repository.AppDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

fun <T> resultFlow(
    networkCall: suspend () -> Result<T>,
    dispatchers: AppDispatchers
): Flow<Result<T>> = flow {
    emit(Result.loading())
    val response = networkCall()
    when (response.status) {
        Result.Status.SUCCESS -> {
            emit(Result.success(response.data!!))
        }
        Result.Status.ERROR -> {
            emit(
                Result.error(
                    data = null,
                    message = response.message.orEmpty(),
                    code = response.code
                )
            )
        }
        Result.Status.LOADING -> {
            // Do nothing
        }
    }
}.flowOn(dispatchers.io)