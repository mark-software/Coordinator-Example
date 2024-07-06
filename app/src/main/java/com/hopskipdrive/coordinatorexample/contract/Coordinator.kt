package com.hopskipdrive.coordinatorexample.contract

import android.os.Parcelable
import androidx.lifecycle.ViewModelStoreOwner
import com.hopskipdrive.coordinatorexample.base.BaseViewModel
import com.hopskipdrive.coordinatorexample.model.SingleEvent

/**
 * Created by Mark Miller.
 *
 * @param S A sealed class that represents each screen in the flow.
 */
interface Coordinator<S : FlowScreen> {
    /**
     * The key used to save and restore the state of this coordinator.
     */
    val bundleStateKey: String

    /**
     * Called by the host when the flow starts.
     * Use this to display the first screen, log analytics, set up state, etc.
     */
    fun onStart() {}

    /**
     * Called when the host of this coordinator is killed by the system. Override to save any internal
     * state that can be restored later using [onRestoreInstanceState].
     */
    fun onSaveInstanceState(): Parcelable? = null

    /**
     * Called after the host of this coordinator has been re-created after being destroyed. Override to restore
     * any state that was previously saved in [onSaveInstanceState].
     */
    fun onRestoreInstanceState(savedState: Parcelable) {}

    /**
     * Create a [BaseViewModel] instance for the given [screen].
     * [BaseViewModel] is the class that all our ViewModels extend from.
     */
    fun onCreateViewModel(screen: FlowScreen, owner: ViewModelStoreOwner): BaseViewModel

    /**
     * Handle events sent from [BaseViewModel] instances.
     */
    fun onEvent(event: SingleEvent<Any>, screen: FlowScreen)

    fun canGoBack(): Boolean

    fun goBack()
}
