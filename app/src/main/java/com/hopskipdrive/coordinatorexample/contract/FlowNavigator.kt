package com.hopskipdrive.coordinatorexample.contract

/**
 * Created by Mark Miller.
 *
 * This navigator is responsible for navigating between screens in a flow.
 */
interface FlowNavigator<S: FlowScreen> {
    fun replace(screen: S)
    fun navigateTo(screen: S)
    fun close()

    /**
     * Set a listener to be notified when the user navigates to a new screen.
     *
     * Necessary because not all navigation events are immediate. E.g., we
     * might need to wait until the lifecycle is in a state of "started"
     * to add a new fragment.
     */
    fun setNavigationListener(listener: ScreenNavigationListener<S>?) {}
}
