package com.example.survey.utils

import androidx.compose.runtime.Immutable
import com.example.survey.utils.Async.Failure
import com.example.survey.utils.Async.Loading
import com.example.survey.utils.Async.Success
import com.example.survey.utils.Async.Uninitialized

@Immutable
sealed interface Async<out T : Any> {

    data object Uninitialized : Async<Nothing>
    data class Loading<T : Any>(val value: T?) : Async<T>
    data class Success<T : Any>(val value: T) : Async<T>
    data class Failure(val throwable: Throwable) : Async<Nothing>
}

fun <T : Any, R : Any> Async<T>.mapValue(mapper: (T) -> R): Async<R> = when (this) {
    is Loading -> Loading(value?.let(mapper))
    is Success -> Success(mapper(value))
    is Failure -> Failure(throwable)
    Uninitialized -> Uninitialized
}

inline fun <reified T : Any> Async<T>.getOrElse(default: T): T =
    getOrNull() ?: default

inline fun <reified T : Any> Async<T>.getOrThrow(): T =
    getOrNull() ?: throw NotAValueAsync(this)

inline fun <reified T : Any> Async<T>.getOrNull(): T? = when (this) {
    is Failure, Uninitialized -> null
    is Loading -> value
    is Success -> value
}

data class NotAValueAsync(val value: Async<*>) :
    Throwable(message = "Async $value doesn't contains a value")