package gst.trainingcourse.pagingimpl.remote

import java.lang.Exception

sealed class Result<out T: Any> {
    data class Success<out T: Any>(val data: T): Result<T>()
    data class Error(val exception: Exception): Result<Nothing>()
}

fun <T: Any, DATA> Result<T>.getData(
    onSuccess: (resultData: T) -> DATA,
    onFailed: (throwable: Throwable) -> DATA
): DATA = when (this) {
    is Result.Success -> onSuccess(this.data)
    is Result.Error -> onFailed(this.exception)
}