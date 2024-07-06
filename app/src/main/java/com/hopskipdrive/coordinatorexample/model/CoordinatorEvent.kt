package com.hopskipdrive.coordinatorexample.model

/**
 * Created by Mark Miller.
 */
sealed interface CoordinatorEvent {
    object GoBackToFirstFragment: CoordinatorEvent
    object GoToSecondFragment: CoordinatorEvent
    object SecondFragmentClickedDialogButton: CoordinatorEvent
}
