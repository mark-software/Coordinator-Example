package com.hopskipdrive.coordinatorexample.ui.second

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.hopskipdrive.coordinatorexample.base.BaseViewModel
import com.hopskipdrive.coordinatorexample.domain.DoSomethingUseCase
import com.hopskipdrive.coordinatorexample.model.CoordinatorEvent
import com.hopskipdrive.coordinatorexample.ui.second.useCase.DefaultGetTapDescriptionUseCase
import com.hopskipdrive.coordinatorexample.ui.second.useCase.GetTapDescriptionUseCase
import kotlinx.parcelize.Parcelize

/**
 * Created by Mark Miller.
 *
 * We can still use dependency injection in here for additional properties.
 *
 * @param handle - In terms of state, the SavedStateHandle will always be the source of truth.
 */
class SecondViewModel(
    private val doSomethingUseCase: DoSomethingUseCase,
    private val handle: SavedStateHandle,
    private val getTapDescriptionUseCase: GetTapDescriptionUseCase = DefaultGetTapDescriptionUseCase()
): BaseViewModel() {

    //The state here could be coming from the coordinator (when we need to restore state)
    private val screenState: SecondSampleState = handle.get<SecondSampleState>(STATE_KEY)
        ?: SecondSampleState(clickedDialogButton = false)
    private val _buttonTapStatus = MutableLiveData<String>()
    val buttonTapStatus: LiveData<String> = _buttonTapStatus


    init {
        updateTapDescription()
    }

    /**
     * Send an event to the coordinator to tell it we
     * want to go back to the first fragment.
     */
    fun goBackToFirstFragment() {
        sendCoordinatorEvent(CoordinatorEvent.GoBackToFirstFragment)
    }

    //Example usage of a swappable use case
    fun getPopupMessage() = doSomethingUseCase()

    fun clickedDialogButton() {
        screenState.clickedDialogButton = true
        handle[STATE_KEY] = screenState //<-- edge case handling
        sendCoordinatorEvent(CoordinatorEvent.SecondFragmentClickedDialogButton)
        updateTapDescription()
    }

    private fun updateTapDescription() {
        _buttonTapStatus.value = getTapDescriptionUseCase(screenState)
    }

    /**
     * A factory is required if we want to specify dependencies like the DoSomethingUseCase
     * or the state of the ViewModel. To keep things flexible these are not injected
     * and will be handled by the coordinator.
     */
    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val doSomethingUseCase: DoSomethingUseCase,
        private val state: SecondSampleState? = null
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            val savedStateHandle = extras.createSavedStateHandle()
            state?.also { savedStateHandle[STATE_KEY] = it }

            return SecondViewModel(doSomethingUseCase, savedStateHandle) as T
        }
    }

    //A simple class to represent the state of the screen.
    @Parcelize
    data class SecondSampleState(
        var clickedDialogButton: Boolean
    ): Parcelable

    companion object {
        private const val STATE_KEY = "second_state_key"
    }
}
