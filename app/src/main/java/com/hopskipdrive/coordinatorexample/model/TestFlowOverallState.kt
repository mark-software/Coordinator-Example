package com.hopskipdrive.coordinatorexample.model

import android.os.Parcelable
import com.hopskipdrive.coordinatorexample.ui.second.SecondViewModel
import kotlinx.parcelize.Parcelize

/**
 * Created by Mark Miller.
 */
@Parcelize
data class TestFlowOverallState(
    var clickedSecondFragmentButton: Boolean = false,
    var someOtherProperty: String = "",
    val screenStack: StackParcelable<MyScreenState> = StackParcelable()
): Parcelable

fun TestFlowOverallState.toSecondVmState(): SecondViewModel.SecondSampleState {
    return SecondViewModel.SecondSampleState(clickedSecondFragmentButton)
}
