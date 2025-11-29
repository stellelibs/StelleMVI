package com.stelle.libs.mvi.compose.koin

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.stelle.libs.mvi.StelleState
import com.stelle.libs.mvi.StelleViewModel
import com.stelle.libs.mvi.compose.StelleScreen
import com.stelle.libs.mvi.event.StelleEffect
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.TypeQualifier
import org.koin.core.scope.Scope
import org.koin.ext.getFullName
import org.koin.mp.KoinPlatform.getKoin
import org.koin.viewmodel.defaultExtras
import org.koin.viewmodel.resolveViewModel
import kotlin.reflect.KClass

/**
 * An abstract [StelleScreen] that integrates with Koin to automate ViewModel injection.
 *
 * This class provides two primary mechanisms for resolving a [ViewModel]:
 * 1.  **Direct Injection **: By overriding [viewModelClass], you provide the
 *     exact `KClass` of the ViewModel to be injected. This is type-safe and avoids
 *     the need for Koin scopes and bindings in simple cases.
 * 2.  **Scope-Based Injection**: If [viewModelClass] is not overridden, it falls back to
 *     resolving a generic `StelleViewModel` from a Koin scope tied to this screen's class.
 *     This requires defining a `scope<YourScreen>` and binding the ViewModel in your Koin module.
 *
 * @param State The type of the [StelleState] that this screen observes.
 * @param ViewModel The type of the [StelleViewModel] that this screen uses.
 *
 */
abstract class StelleScreenKoin<State : StelleState, ViewModel : StelleViewModel<State, *>>(
    onEffectFromParent: ((effect: StelleEffect) -> Unit)? = null
) :
    StelleScreen<State, ViewModel>(onEffectFromParent = onEffectFromParent) {

    /**
     * Overriding this property is an optional configuration that allows you to specify the concrete
     * [ViewModel] class to be injected.
     *
     * By doing so, you can avoid the need to use a Koin `scope` and `bind` for the ViewModel in your
     * dependency injection module, leading to a simpler module definition.
     *
     * @see generateViewModelKey to customize the key used for ViewModel resolution.
     */
    open val viewModelClass: KClass<ViewModel>? = null

    /**
     * Generates the key used to identify and resolve the ViewModel in Koin.
     *
     * By default, it uses the fully qualified name of the screen class.
     * You can override this function to provide a custom key if needed.
     *
     * @return The key for the ViewModel resolution.
     */
    open fun generateViewModelKey(): String = this::class.getFullName()

    /**
     * A final implementation that creates the ViewModel by calling [diViewModel].
     * Subclasses should not override this; they should provide the ViewModel's class
     * via [viewModelClass] instead.
     */
    @Composable
    final override fun createViewModel() = diViewModel(
        viewModelStoreOwner = LocalViewModelStoreOwner.current
            ?: error("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"),
    )
        ?: throw Exception("problem injecting viewmodel")

    /**
     * Resolves and provides the [ViewModel] instance from Koin.
     *
     * It uses [viewModelClass] if provided for a direct, type-safe resolution.
     * Otherwise, it attempts to resolve `StelleViewModel::class` from the Koin
     * scope associated with this screen, requiring a `bind` in the DI module.
     *
     * @return The resolved [ViewModel] instance, or `null` if it cannot be resolved.
     */
    @Suppress("UNCHECKED_CAST")
    @OptIn(KoinInternalApi::class)
    protected fun diViewModel(
        qualifier: Qualifier? = null,
        viewModelStoreOwner: ViewModelStoreOwner,
        key: String? = generateViewModelKey(),
        extras: CreationExtras = defaultExtras(viewModelStoreOwner),
        scope: Scope = getScope(),
        parameters: ParametersDefinition? = null,
    ): ViewModel? {
        return resolveViewModel(
            viewModelClass ?: StelleViewModel::class,
            viewModelStoreOwner.viewModelStore,
            key,
            extras,
            qualifier,
            scope,
            parameters
        ) as? ViewModel
    }

    /**
     * Retrieves or creates the Koin scope associated with this screen.
     *
     * This scope is used to resolve dependencies that are specific to this screen's lifecycle.
     * By default, the scope is identified by the fully qualified name of the screen class.
     *
     * @return The Koin [Scope] for this screen.
     */
    protected open fun getScope() = getKoin().getOrCreateScope(
        scopeId = this::class.getFullName(),
        qualifier = TypeQualifier(this::class),
        source = this
    )
}