package com.stelle.libs.usecase

import kotlin.coroutines.cancellation.CancellationException

/**
 * Base class for [StelleUseCase] implementations that want the `try/catch` boilerplate handled
 * for them.
 *
 * Subclasses implement [perform] with the happy path and let any thrown [Throwable] be
 * captured into a [Result.failure]. [CancellationException] is re-thrown by default so
 * structured concurrency keeps working; override [propagateCancellation] to change this behaviour.
 *
 * Override [handleException] to customise how exceptions are mapped into a [Result] (e.g. wrap
 * them into a domain-specific error, or recover to a default value with [Result.success]).
 *
 * @param Params The type of parameters required to execute this use case.
 * @param R The type of the successful result produced by this use case.
 */
abstract class StelleUseCaseSafe<Params, R> : StelleUseCase<Params, R> {

    /**
     * Whether [CancellationException]s thrown by [perform] should be re-thrown so that
     * structured concurrency can cancel the surrounding scope.
     *
     * Defaults to `true`, which is the safe choice. Override and return `false` only if you
     * fully understand the trade-offs — swallowing a cancellation means the surrounding scope
     * is already cancelled and any downstream work will also fail, yet the caller will see a
     * [Result.failure] as if it were a regular error.
     *
     * @return `true` (default) to re-throw the [CancellationException], `false` to route it
     *         through [handleException] like any other exception.
     */
    protected open fun propagateCancellation() = true

    /**
     * Maps an exception thrown by [perform] into a [Result].
     *
     * The default implementation wraps [e] into a [Result.failure]. Override to wrap it into a
     * domain-specific error, or to recover to a successful value by returning [Result.success]
     * with a fallback.
     *
     * @param e The exception thrown by [perform].
     * @return A [Result] that will be returned by [invoke].
     */
    protected open fun handleException(e: Throwable): Result<R> {
        return Result.failure(e)
    }

    /**
     * Executes the use case's business logic. May throw freely — any [Throwable] is captured by
     * [invoke] and fed into [handleException].
     *
     * @param params The parameters required for the operation.
     * @return The successful value of type [R].
     */
    protected abstract suspend fun perform(params: Params): R

    /**
     * Runs [perform] and converts the outcome to a [Result]. This method cannot be
     * overridden; customise behaviour via [perform], [handleException] and
     * [propagateCancellation].
     */
    final override suspend fun invoke(params: Params): Result<R> {
        return try {
            Result.success(perform(params))
        } catch (e: CancellationException) {
            if (propagateCancellation())
                throw e
            else
                handleException(e)
        } catch (e: Throwable) {
            handleException(e)
        }
    }
}
