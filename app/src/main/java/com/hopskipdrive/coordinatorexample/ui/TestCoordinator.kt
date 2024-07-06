package com.hopskipdrive.coordinatorexample.ui

import android.os.Parcelable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.hopskipdrive.coordinatorexample.base.BaseViewModel
import com.hopskipdrive.coordinatorexample.contract.Coordinator
import com.hopskipdrive.coordinatorexample.contract.FlowScreen
import com.hopskipdrive.coordinatorexample.contract.FragmentFlowNavigator
import com.hopskipdrive.coordinatorexample.contract.ScreenNavigationListener
import com.hopskipdrive.coordinatorexample.domain.DefaultDoSomethingUseCase
import com.hopskipdrive.coordinatorexample.domain.DifferentDoSomethingUseCase
import com.hopskipdrive.coordinatorexample.model.CoordinatorEvent
import com.hopskipdrive.coordinatorexample.model.MyScreenState
import com.hopskipdrive.coordinatorexample.model.SingleEvent
import com.hopskipdrive.coordinatorexample.model.TestFlowOverallState
import com.hopskipdrive.coordinatorexample.model.toSecondVmState
import com.hopskipdrive.coordinatorexample.ui.first.FirstViewModel
import com.hopskipdrive.coordinatorexample.ui.second.SecondViewModel

/**
 * Created by Mark Miller.
 */
class TestCoordinator(
    private val flowNavigator: FragmentFlowNavigator<MyScreenState>
): Coordinator<MyScreenState>, ScreenNavigationListener<MyScreenState> {

    override val bundleStateKey: String = "TestCoordinator_state"
    private var overallState = TestFlowOverallState()


    init {
        flowNavigator.setNavigationListener(this)
    }

    override fun onStart() {
        flowNavigator.navigateTo(MyScreenState.FIRST)
    }

    override fun canGoBack(): Boolean {
        val currentScreen = overallState.screenStack.peek()

        return currentScreen != null && currentScreen != MyScreenState.FIRST
    }

    override fun goBack() = flowNavigator.close()

    override fun onSaveInstanceState(): Parcelable? {
        return overallState
    }

    override fun onRestoreInstanceState(savedState: Parcelable) {
        if (savedState is TestFlowOverallState) {
            overallState = savedState
        }
    }

    override fun onCreateViewModel(
        screen: FlowScreen,
        owner: ViewModelStoreOwner
    ): BaseViewModel {
        return when (screen) {
            MyScreenState.FIRST -> {
                val factory = FirstViewModel.Factory(DefaultDoSomethingUseCase())
                ViewModelProvider(owner, factory)[FirstViewModel::class.java]
            }
            MyScreenState.SECOND -> {
                val factory = SecondViewModel.Factory(
                    DifferentDoSomethingUseCase(),
                    overallState.toSecondVmState()
                )
                ViewModelProvider(owner, factory)[SecondViewModel::class.java]

                //Use this if no dependencies are needed
                //ViewModelProvider(owner)[SecondViewModel::class.java]
            }
            else -> throw IllegalArgumentException("Unknown screen: $screen")
        }
    }

    override fun onScreenPushed(screen: MyScreenState) {
        overallState.screenStack.push(screen)
    }

    override fun onScreenPopped() {
        if (overallState.screenStack.isEmpty()) return

        overallState.screenStack.pop()
    }

    override fun onEvent(event: SingleEvent<Any>, screen: FlowScreen) {
        when (event.getContentIfNotHandled()) {
            is CoordinatorEvent.GoBackToFirstFragment -> {
                flowNavigator.close() //Only two fragments so we pop the second one
            }
            is CoordinatorEvent.GoToSecondFragment -> {
                flowNavigator.navigateTo(MyScreenState.SECOND)
            }
            is CoordinatorEvent.SecondFragmentClickedDialogButton -> {
                overallState.clickedSecondFragmentButton = true
            }
        }
    }
}
