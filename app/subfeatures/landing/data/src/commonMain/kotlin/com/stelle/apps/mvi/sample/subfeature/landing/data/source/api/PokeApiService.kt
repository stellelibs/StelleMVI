package com.stelle.apps.mvi.sample.subfeature.landing.data.source.api

import com.stelle.apps.mvi.sample.subfeature.landing.data.response.PokemonDetailEntity
import com.stelle.apps.mvi.sample.subfeature.landing.data.response.PokemonResponseEntity

/** Service interface for interacting with the PokeAPI to fetch Pokemon data. */
interface PokeApiService {
    /** Fetch a list of Pokemons with pagination support.
     *
     * @param limit The maximum number of Pokemons to retrieve. Default is 20.
     * @param offset The starting index from which to retrieve Pokemons. Default is 0.
     * @return A [PokemonResponseEntity] containing the list of Pokemons and pagination info.
     */
    suspend fun getPokemons(limit: Int = 20, offset: Int = 0): PokemonResponseEntity

    /** Fetch detailed information about a specific Pokemon by its name or ID.
     *
     * @param nameOrId The name or ID of the Pokemon to retrieve details for.
     * @return A [PokemonDetailEntity] containing detailed information about the specified Pokemon.
     */
    suspend fun getPokemonDetail(nameOrId: String): PokemonDetailEntity
}