![logo][logo]

[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.21-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Apache 2 License][apachelic]](http://www.apache.org/licenses/LICENSE-2.0.txt)

# What is Stelle MVI

**A lightweight, expressive, quick learning curve, multiplatform MVI architecture for Kotlin
Multiplatform**

StelleMVI is a small but powerful library designed to simplify state management using the *
*Model–View–Intent (MVI)** architecture across **Kotlin Multiplatform** targets.  
It provides a clean, predictable, unidirectional data-flow system built on top of Kotlin Flows, with
minimal boilerplate and high extensibility.

### True MVI Architecture

StelleMVI structures your application around:

- **Intent** – user actions or external events
- **Reducer** – pure functions that update the state
- **State** – the single source of truth
- **Effect / Side-Effect** – ephemeral actions such as navigation or IO
- **Store** – (divided in viewmodel and data) the engine that binds everything together

## Library Modules

StelleMVI is organized into several modules so you only include what you need.

- **`mvi`**: The core of the architecture. Contains `StelleViewModel`, `StelleState`, etc.
- **`mvi-compose`**: Base classes like `StelleScreen` to integrate MVI with Jetpack/JetBrains
  Compose.
- **`mvi-koin`**: Integration classes for injecting ViewModels and their children using Koin.
- **`mvi-compose-koin`**: A convenience module that combines Compose and Koin integrations.
- **`usecase`**: Provides the `StelleUseCase` interface for business logic.
- **`dispatchers`**: Provides a standardized set of `CoroutineDispatchers` for KMP.

## Current Version

- **Stable**
  Version : [![Maven Central](https://img.shields.io/maven-central/v/io.github.stellelibs/mvi)](https://repo1.maven.org/maven2/io/github/stellelibs/mvi/)

### Add Dependency

You can download this library from MavenCentral

* groovy

```groovy
sourceSets {
    commonMain {
        dependencies {
            implementation "io.github.stellelibs:mvi:$stelleMVIVersion"
        }
    }
}
```

* kts

```kotlin
sourceSets {
    commonMain.dependencies {
        dependencies {
            implementation("io.github.stellelibs:mvi:$stelleMVIVersion")
        }
    }
}


```

## Usage

In the sample folder you can find a sample project showing how to use Stelle MVI in a Kotlin
Multiplatform project with Compose Multiplatform.

1. **[The MVI Pattern](https://github.com/stellelibs/StelleMVI/wiki/The-MVI-Pattern)**  
   *Start here to understand the core principles of the Model-View-Intent architecture and how
   StelleMVI implements it.*

2. **[Basic Tutorial (No Dependency Injection)](https://github.com/stellelibs/StelleMVI/wiki/Basic-Tutorial)**  
   *Learn the fundamentals of StelleMVI by building a feature without any dependency injection
   framework.*

3. **[Koin Integration Guide](https://github.com/stellelibs/StelleMVI/wiki/Koin-Guide)**  
   *A dedicated guide to integrating StelleMVI with Koin for robust dependency injection in your KMP
   project.*

4. **[Advanced Guide: Parent-Child ViewModels](https://github.com/stellelibs/StelleMVI/wiki/Advanced-Guide:Parent-Child-ViewModels)**  
   *Explore the powerful Parent-Child pattern for managing complexity in large screens.*

5. **[UseCase Module Guide](https://github.com/stellelibs/StelleMVI/wiki/UseCase-Module-Guide-)**  
   *Learn how to use the `usecase` module to structure your business logic cleanly and effectively.*

6. **[Dispatchers Module Guide](https://github.com/stellelibs/StelleMVI/wiki/Dispatchers-Module-Guide-)** 
   *Understand the `dispatchers` module and how it provides a consistent way to manage coroutine
   dispatchers across all platforms.*


[logo]: art/logoh3.png

[apachelic]: art/apachelic.svg
