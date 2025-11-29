package com.stelle.libs.usecase.result

/**
 * Fold function to handle both success and error cases of [StelleResult].
 *
 * @param onSuccess Lambda function to be executed if the result is a success.
 * @param onError Lambda function to be executed if the result is an error.
 * @return The result of either [onSuccess] or [onError] based on the state of [StelleResult].
 */
fun <R> StelleResult<R>.fold(
    onSuccess: (R) -> Unit,
    onError: (Throwable) -> Unit
) = when (this) {
    is StelleResult.Success -> {
        onSuccess(data)
    }

    is StelleResult.Error -> {
        onError(exception)
    }
}