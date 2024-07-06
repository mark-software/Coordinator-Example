package com.hopskipdrive.coordinatorexample.contract

import android.os.Parcelable

/**
 * Created by Mark Miller.
 *
 * Represents a screen in a flow.
 */
interface FlowScreen: Parcelable {

    /**
     * A unique key for the screen.
     * Can be used as a Fragment tag so this must be unique across screens.
     */
    val screenKey: String
}
