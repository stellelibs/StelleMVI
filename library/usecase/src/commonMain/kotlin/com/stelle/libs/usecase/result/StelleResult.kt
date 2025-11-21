package com.stelle.libs.usecase.result

/**
 * A sealed class representing the result of an operation, which can either be a success or an error.
 *
 * @param T The type of data held in case of a successful result.
 */
sealed class StelleResult<out T> {
    /**
     * Represents a successful result containing the data of type [T].
     */
    data class Success<out T>(val data: T) : StelleResult<T>()

    /**
     * Represents an error result containing the exception that occurred.
     */
    data class Error(
        val exception: Throwable
    ) : StelleResult<Nothing>()

    /**
     * Indicates whether the result is a success.
     */
    val isSuccess get() = this is Success

    /**
     * Indicates whether the result is an error.
     */
    val isError get() = this is Error
}