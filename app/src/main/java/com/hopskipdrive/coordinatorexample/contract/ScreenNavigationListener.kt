package com.hopskipdrive.coordinatorexample.contract

/**
 * Created by Mark Miller.
 */
interface ScreenNavigationListener<S: FlowScreen> {
    fun onScreenPushed(screen: S)
    fun onScreenPopped()
}
