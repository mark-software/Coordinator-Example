package com.hopskipdrive.coordinatorexample

import androidx.fragment.app.Fragment
import com.hopskipdrive.coordinatorexample.base.BaseViewModel
import com.hopskipdrive.coordinatorexample.constants.CoordinatorConstants
import com.hopskipdrive.coordinatorexample.contract.CoordinatorHost
import com.hopskipdrive.coordinatorexample.contract.FlowScreen
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created by Mark Miller.
 */
class HostedViewModelDelegate<VM : BaseViewModel>(
    private val fragment: Fragment
) : ReadOnlyProperty<Fragment, VM> {

    private var viewModel: VM? = null


    override fun getValue(thisRef: Fragment, property: KProperty<*>): VM {
        if (viewModel == null) {
            viewModel = findViewModel(fragment)
        }

        return viewModel!!
    }

    private fun findViewModel(fragment: Fragment): VM {
        //Start by searching the parent fragment (rather than the current fragment)
        // so that the current fragment can be attached to a coordinator
        // as well as be a host for child fragments.
        var parentFragment: Fragment? = fragment.parentFragment
        while (parentFragment != null) {
            if (parentFragment is CoordinatorHost) {
                return createAndBindViewModel(parentFragment)
            }
            parentFragment = parentFragment.parentFragment
        }

        val activity = fragment.activity
        if (activity is CoordinatorHost) {
            return createAndBindViewModel(activity)
        }

        throw IllegalStateException("No CoordinatorHost found in parent hierarchy " +
                "for fragment ${fragment::class.java.simpleName}")
    }

    @Suppress("UNCHECKED_CAST")
    private fun createAndBindViewModel(host: CoordinatorHost): VM {
        val screen = fragment.arguments
            ?.getParcelable<FlowScreen>(CoordinatorConstants.FLOW_SCREEN)

        val viewModel = host.coordinator.onCreateViewModel(screen!!, fragment)
        viewModel.coordinatorEvents.observe(fragment) { event ->
            host.coordinator.onEvent(event, screen)
        }

        return viewModel as VM
    }
}

inline fun <reified VM : BaseViewModel> Fragment.hostedViewModel(): ReadOnlyProperty<Fragment, VM> {
    return HostedViewModelDelegate(this)
}
