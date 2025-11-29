package com.stelle.libs.dispatchers

import kotlinx.coroutines.Dispatchers

/**
 * A container class that provides a standardized set of coroutine dispatchers for use
 * throughout the application. This promotes consistency and simplifies testing.
 *
 * @property default The dispatcher for CPU-intensive work, defaulting to [Dispatchers.Default].
 * @property io The dispatcher for background I/O operations (e.g., network, disk), defaulting to [IODispatcher].
 * @property ui The dispatcher for UI-related operations, defaulting to [Dispatchers.Main].
 */
class StelleDispatchers(
    /** For CPU-intensive operations off the main thread. */
    val default: kotlinx.coroutines.CoroutineDispatcher = Dispatchers.Default,
    /** For network or disk operations. */
    val io: kotlinx.coroutines.CoroutineDispatcher = IODispatcher,
    /** For UI-related tasks and interactions. */
    val ui: kotlinx.coroutines.CoroutineDispatcher = Dispatchers.Main
)

/**
 * Provides a platform-specific coroutine dispatcher that is optimized for blocking I/O operations.
 *
 * This `expect` declaration requires each target platform (e.g., JVM, Native, JS) to provide an
 * `actual` implementation, ensuring the best performance characteristics for that platform.
 */
expect val IODispatcher: kotlinx.coroutines.CoroutineDispatcher