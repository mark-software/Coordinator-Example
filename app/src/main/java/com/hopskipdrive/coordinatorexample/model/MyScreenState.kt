package com.hopskipdrive.coordinatorexample.model

import com.hopskipdrive.coordinatorexample.contract.FlowScreen
import kotlinx.parcelize.Parcelize

/**
 * Created by Mark Miller.
 *
 * Both enums and sealed classes can be used as FlowScreens.
 */
@Parcelize
enum class MyScreenState(override val screenKey: String): FlowScreen {
    FIRST("MyScreenState_First"), SECOND("MyScreenState_Second")
}

//Example of a sealed class as a FlowScreen.
//@Parcelize
//sealed class TestScreenState(override val screenKey: String): FlowScreen {
//
//    @Parcelize
//    class First: TestScreenState("TestScreenState_First")
//
//    @Parcelize
//    object Second: TestScreenState("TestScreenState_Second")
//}
