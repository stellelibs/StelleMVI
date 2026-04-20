package com.stelle.libs.usecase

/**
 * Represents a single, specific business operation or "use case" in the application.
 *
 * A use case encapsulates a piece of business logic, promoting a clean architecture by
 * separating domain logic from UI and data layers. It is typically executed on a background thread.
 *
 * Implementations must return a [Result] instead of throwing. Use [StelleUseCaseSafe] if you
 * prefer to write just the happy path and let the framework catch exceptions for you.
 *
 * @param Params The type of parameters required to execute this use case. Use `Unit` if no parameters are needed.
 * @param R The type of the successful result produced by this use case.
 */
interface StelleUseCase<in Params, R> {
    /**
     * Executes the use case with the given parameters.
     *
     * The `operator` keyword allows this method to be called using the `()` syntax,
     * making the execution feel like a direct function call (e.g., `myUseCase(params)`).
     *
     * @param params The parameters required for the operation.
     * @return A [Result] containing either the value of type [R] on success, or the thrown
     *         [Throwable] on failure.
     */
    suspend operator fun invoke(params: Params): Result<R>
}
