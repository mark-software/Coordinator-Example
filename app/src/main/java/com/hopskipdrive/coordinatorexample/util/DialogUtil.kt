package com.hopskipdrive.coordinatorexample.util

import android.app.Activity
import androidx.appcompat.app.AlertDialog

/**
 * Created by Mark Miller.
 */
object DialogUtil {

    //Just for testing purposes
    fun getDialog(message: String, activity: Activity): AlertDialog {
        return AlertDialog.Builder(activity)
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }
}
