package com.hopskipdrive.coordinatorexample.contract

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.hopskipdrive.coordinatorexample.R

/**
 * Created by Mark Miller.
 */
class FragmentFlowNavigator<S: FlowScreen>(
    private val fm: FragmentManager,
    private val lifecycle: Lifecycle,
    private val containerViewId: Int,
    private val fragmentFactory: (S) -> Fragment
) : FlowNavigator<S> {

    private val pendingTransactions = mutableListOf<Runnable>()
    private var executingPendingTransactions = false
    private var navListener: ScreenNavigationListener<S>? = null


    init {
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                executePendingTransactions()
            }

            override fun onDestroy(owner: LifecycleOwner) {
                pendingTransactions.clear()
                lifecycle.removeObserver(this)
            }
        })
    }

    override fun replace(screen: S) {
        executeTransaction(screen, replaceScreen = true)
    }

    override fun navigateTo(screen: S) {
        executeTransaction(screen, replaceScreen = false)
    }

    override fun close() {
        val transaction = Runnable {
            if (fm.backStackEntryCount > 0) {
                fm.popBackStack()
                navListener?.onScreenPopped()
            }
        }
        handleTransaction(transaction)
    }

    private fun executeTransaction(
        screen: S,
        replaceScreen: Boolean
    ) {
        val transaction = Runnable {
            if (replaceScreen && fm.backStackEntryCount > 0) {
                fm.popBackStack()
                navListener?.onScreenPopped()
            }

            val fragment = fragmentFactory(screen)
            fm.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_right, R.anim.slide_out_left,
                    R.anim.slide_in_left, R.anim.slide_out_right
                )
                .replace(containerViewId, fragment, screen.screenKey)
                .addToBackStack(screen.screenKey)
                .commit()
            navListener?.onScreenPushed(screen)
        }
        handleTransaction(transaction)
    }

    private fun executePendingTransactions() {
        if (executingPendingTransactions) return

        executingPendingTransactions = true
        try {
            val iterator = pendingTransactions.iterator()
            while (iterator.hasNext()) {
                if (!lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) { break }
                iterator.next().run()
                iterator.remove()
            }
        } finally {
            executingPendingTransactions = false
        }
    }

    private fun handleTransaction(transaction: Runnable) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            transaction.run()
        } else {
            pendingTransactions.add(transaction)
        }
    }

    override fun setNavigationListener(listener: ScreenNavigationListener<S>?) {
        navListener = listener
    }
}
