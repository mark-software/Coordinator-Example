package com.hopskipdrive.coordinatorexample.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Stack

/**
 * Created by Mark Miller.
 */
@Parcelize
class StackParcelable<T: Parcelable>: Stack<T>(), Parcelable
