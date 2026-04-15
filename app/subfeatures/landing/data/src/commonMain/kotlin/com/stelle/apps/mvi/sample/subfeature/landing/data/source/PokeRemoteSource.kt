package com.stelle.apps.mvi.sample.subfeature.landing.data.source

import com.stelle.apps.mvi.sample.subfeature.common.domain.model.PokemonDetail
import com.stelle.apps.mvi.sample.subfeature.landing.domain.model.Pokemons

/** Interface defining remote data source operations for fetching Pokemon data. */
interface PokeRemoteSource {
    /** Fetch a list of Pokemons with pagination support.
     *
     * @param limit The maximum number of Pokemons to retrieve. Default is 20.
     * @param offset The starting index from which to retrieve Pokemons. Default is 0.
     * @return A [Pokemons] containing the list of Pokemons and pagination info.
     */
    suspend fun getPokemons(limit: Int = 20, offset: Int = 0): Result<Pokemons>

    /** Fetch detailed information about a specific Pokemon by its name or ID.
     *
     * @param nameOrId The name or ID of the Pokemon to retrieve details for.
     * @return A [PokemonDetail containing detailed information about the specified Pokemon.
     */
    suspend fun getPokemonDetail(nameOrId: String): Result<PokemonDetail>
}