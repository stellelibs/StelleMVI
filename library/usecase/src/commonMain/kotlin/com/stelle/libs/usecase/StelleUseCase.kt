package com.stelle.libs.usecase

import com.stelle.libs.usecase.result.StelleResult

/**
 * Represents a single, specific business operation or "use case" in the application.
 *
 * A use case encapsulates a piece of business logic, promoting a clean architecture by
 * separating domain logic from UI and data layers. It is typically executed on a background thread.
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
     * @return A [StelleResult] which is either a [StelleResult.Success] containing the data of type [R]
     *         or a [StelleResult.Error] containing the exception.
     */
    suspend operator fun invoke(params: Params): StelleResult<R>
}