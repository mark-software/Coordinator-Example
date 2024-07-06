package com.hopskipdrive.coordinatorexample.model

import android.os.Parcelable
import com.hopskipdrive.coordinatorexample.ui.second.SecondViewModel
import kotlinx.parcelize.Parcelize
import java.util.Stack

/**
 * Created by Mark Miller.
 */
@Parcelize
data class TestFlowOverallState(
    var clickedSecondFragmentButton: Boolean = false,
    var someOtherProperty: String = "",
    val screenStack: Stack<MyScreenState> = Stack()
): Parcelable

fun TestFlowOverallState.toSecondVmState(): SecondViewModel.SecondSampleState {
    return SecondViewModel.SecondSampleState(clickedSecondFragmentButton)
}
