# Migration Guide

This document describes breaking changes between versions of StelleMVI and how to migrate
existing code.

## Migrating from `StelleResult` to `kotlin.Result`

In this version the custom `StelleResult` sealed class and its companion `fold` extension have
been removed from the `usecase` module. The `StelleUseCase` interface now returns the standard
`kotlin.Result<R>` instead.

The rest of the API (`execute`, `ViewModel.executeUseCase`, the `StelleDispatchers` overloads)
keeps the same package, names and signatures, so callers that only rely on the
`onSuccess` / `onError` callbacks do not need changes.

### What was removed

| Removed symbol                                                 | Replacement                             |
|----------------------------------------------------------------|-----------------------------------------|
| `com.stelle.libs.usecase.result.StelleResult`                  | `kotlin.Result`                         |
| `com.stelle.libs.usecase.result.StelleResult.Success(value)`   | `Result.success(value)`                 |
| `com.stelle.libs.usecase.result.StelleResult.Error(throwable)` | `Result.failure(throwable)`             |
| `com.stelle.libs.usecase.result.fold`                          | `kotlin.Result.fold` (standard library) |

The `com.stelle.libs.usecase.result` package no longer exists. Delete any `import` pointing to
it.

### What was added

A new `StelleUseCaseSafe` abstract class has been introduced in
`com.stelle.libs.usecase`. It wraps the use case body in a `try/catch` and exposes an
overridable `onException` hook, so subclasses can focus on the happy path.

```kotlin
class GetPokemonUseCaseImpl(
    private val repository: PokemonRepository
) : StelleUseCaseSafe<GetPokemonUseCase.Params, Pokemons>(), GetPokemonUseCase {

    override suspend fun invokeUnsafe(params: GetPokemonUseCase.Params): Pokemons =
        repository.fetchPokemons(params.limit, params.offset).getOrThrow()

    // Optional: customise error handling
    override fun onException(e: Throwable): Result<Pokemons> =
        Result.failure(MyDomainError(e))
}
```

## Migration steps

### 1. Replace return types

Any public signature returning `StelleResult<T>` must now return `Result<T>`.

**Before**

```kotlin
import com.stelle.libs.usecase.result.StelleResult

interface PokemonRepository {
    suspend fun fetchPokemons(limit: Int, offset: Int): StelleResult<Pokemons>
}
```

**After**

```kotlin
interface PokemonRepository {
    suspend fun fetchPokemons(limit: Int, offset: Int): Result<Pokemons>
}
```

`kotlin.Result` is part of the Kotlin standard library, so no import is required.

### 2. Replace construction of success and error values

**Before**

```kotlin
import com.stelle.libs.usecase.result.StelleResult

override suspend fun getPokemons(limit: Int, offset: Int): StelleResult<Pokemons> {
    return try {
        val response = api.getPokemons(limit, offset)
        StelleResult.Success(mapper.toModel(response))
    } catch (e: Exception) {
        StelleResult.Error(e)
    }
}
```

**After**

```kotlin
override suspend fun getPokemons(limit: Int, offset: Int): Result<Pokemons> {
    return try {
        val response = api.getPokemons(limit, offset)
        Result.success(mapper.toModel(response))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
```

You can also use `runCatching { ... }` from the standard library to collapse the `try/catch`
into a single expression:

```kotlin
override suspend fun getPokemons(limit: Int, offset: Int): Result<Pokemons> =
    runCatching { mapper.toModel(api.getPokemons(limit, offset)) }
```

### 3. `StelleUseCase` implementations

The signature of `StelleUseCase.invoke` returns `Result<R>` now. Update your concrete
implementations accordingly.

**Before**

```kotlin
import com.stelle.libs.usecase.result.StelleResult

class GetPokemonUseCaseImpl(private val repository: PokemonRepository) : GetPokemonUseCase {
    override suspend fun invoke(params: GetPokemonUseCase.Params): StelleResult<Pokemons> =
        repository.fetchPokemons(params.limit, params.offset)
}
```

**After**

```kotlin
class GetPokemonUseCaseImpl(private val repository: PokemonRepository) : GetPokemonUseCase {
    override suspend fun invoke(params: GetPokemonUseCase.Params): Result<Pokemons> =
        repository.fetchPokemons(params.limit, params.offset)
}
```

If you prefer to skip the `try/catch` boilerplate, extend `StelleUseCaseSafe` and implement
`invokeUnsafe` instead — it returns the plain value `R` and any thrown exception is captured
into a `Result.failure` automatically.

### 4. Consuming results with `fold`

`kotlin.Result.fold` has a slightly different signature: the second parameter is named
`onFailure` instead of `onError`. Positional calls keep working:

**Before**

```kotlin
import com.stelle.libs.usecase.result.fold

result.fold(
    onSuccess = { ... },
    onError   = { ... }
)
```

**After**

```kotlin
result.fold(
    onSuccess = { ... },
    onFailure = { ... }
)
```

### 5. ViewModel callers

`ViewModel.executeUseCase(useCase, params, onSuccess, onError, ...)` keeps the same signature,
parameter names and package. No changes are needed in ViewModels that only use callbacks.

## Summary of diffs for a typical call site

| Area                                                     | Change                                                                                                                      |
|----------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------|
| Return type of repositories / remote sources / use cases | `StelleResult<T>` → `Result<T>`                                                                                             |
| Success construction                                     | `StelleResult.Success(x)` → `Result.success(x)`                                                                             |
| Error construction                                       | `StelleResult.Error(e)` → `Result.failure(e)`                                                                               |
| Import of `fold`                                         | remove `import com.stelle.libs.usecase.result.fold` (the standard library one is resolved automatically on `kotlin.Result`) |
| Parameter name in `fold`                                 | `onError` → `onFailure` (if calling by name)                                                                                |
| `executeUseCase` in ViewModels                           | no changes                                                                                                                  |
