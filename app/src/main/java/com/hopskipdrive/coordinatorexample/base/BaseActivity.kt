package com.hopskipdrive.coordinatorexample.base

import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.hopskipdrive.coordinatorexample.contract.CoordinatorHost

/**
 * Created by Mark Miller.
 */
abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //It's important that we restore the state in onCreate() rather than
        //Activity.onRestoreInstanceState() because, after process death, the FragmentManager
        //will restore the fragments and create the ViewModels before Activity.onRestoreInstanceState().
        //Thus, if we use Activity.onRestoreInstanceState(), our ViewModels will have the wrong/stale state.
        if (this is CoordinatorHost) {
            savedInstanceState?.getParcelable<Parcelable>(coordinator.bundleStateKey)?.also {
                coordinator.onRestoreInstanceState(it)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (this is CoordinatorHost) {
            coordinator.onSaveInstanceState()?.also {
                outState.putParcelable(coordinator.bundleStateKey, it)
            }
        }

        super.onSaveInstanceState(outState)
    }
}
