package com.hopskipdrive.coordinatorexample.base

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hopskipdrive.coordinatorexample.model.SingleEvent

/**
 * Created by Mark Miller.
 */
abstract class BaseViewModel: ViewModel() {
    private val _coordinatorEvents = MutableLiveData<SingleEvent<Any>>()
    /**
     * Coordinator events. To be consumed by the Coordinator (only consumed once).
     */
    val coordinatorEvents: LiveData<SingleEvent<Any>> = _coordinatorEvents


    /**
     * Sends an event to [coordinatorEvents] to only be consumed once.
     * ViewModel -> Coordinator communication.
     */
    @MainThread protected fun sendCoordinatorEvent(event: Any) {
        _coordinatorEvents.value = SingleEvent(event)
    }
}
