package com.hopskipdrive.coordinatorexample.extensions

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.hopskipdrive.coordinatorexample.constants.CoordinatorConstants
import com.hopskipdrive.coordinatorexample.contract.FlowScreen

/**
 * Created by Mark Miller.
 */

fun Fragment.withFlowScreen(
    screen: FlowScreen,
    key: String = CoordinatorConstants.FLOW_SCREEN
): Fragment {
    arguments = (arguments ?: Bundle()).apply {
        putParcelable(key, screen)
    }

    return this
}
