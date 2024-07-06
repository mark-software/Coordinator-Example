package com.hopskipdrive.coordinatorexample.ui.first

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.hopskipdrive.coordinatorexample.base.BaseViewModel
import com.hopskipdrive.coordinatorexample.domain.DoSomethingUseCase
import com.hopskipdrive.coordinatorexample.model.CoordinatorEvent

/**
 * Created by Mark Miller.
 */
class FirstViewModel(
    private val doSomethingUseCase: DoSomethingUseCase,
    private val handle: SavedStateHandle
): BaseViewModel() {

    fun goToSecondFragment() {
        sendCoordinatorEvent(CoordinatorEvent.GoToSecondFragment)
    }

    //Example usage of a swappable use case
    fun getPopupMessage() = doSomethingUseCase()


    //This factory is only needed if we want to specify dependencies that can't be injected.
    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val doSomethingUseCase: DoSomethingUseCase
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            val savedStateHandle = extras.createSavedStateHandle()

            return FirstViewModel(doSomethingUseCase, savedStateHandle) as T
        }
    }
}
