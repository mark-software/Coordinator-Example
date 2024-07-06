package com.hopskipdrive.coordinatorexample.ui

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.hopskipdrive.coordinatorexample.R
import com.hopskipdrive.coordinatorexample.base.BaseActivity
import com.hopskipdrive.coordinatorexample.contract.Coordinator
import com.hopskipdrive.coordinatorexample.contract.CoordinatorHost
import com.hopskipdrive.coordinatorexample.contract.FragmentFlowNavigator
import com.hopskipdrive.coordinatorexample.databinding.ActivityMainBinding
import com.hopskipdrive.coordinatorexample.model.MyScreenState
import com.hopskipdrive.coordinatorexample.ui.first.FirstFragment
import com.hopskipdrive.coordinatorexample.ui.second.SecondFragment
import com.hopskipdrive.coordinatorexample.extensions.withFlowScreen

/**
 * Created by Mark Miller.
 */
class MainHostActivity : BaseActivity(), CoordinatorHost {

    override val coordinator: Coordinator<MyScreenState> = TestCoordinator(flowNavigator())
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            // Starting the coordinator for the first time
            coordinator.onStart()
        }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (coordinator.canGoBack()) {
                    coordinator.goBack()
                    return
                }

                finishAfterTransition()
            }
        })
    }

    //This could be injected
    private fun flowNavigator() = FragmentFlowNavigator<MyScreenState>(
        supportFragmentManager,
        lifecycle,
        R.id.fragmentContainer
    ) { screen ->

        supportFragmentManager.findFragmentByTag(screen.screenKey)
            ?: when (screen) {
                MyScreenState.FIRST -> FirstFragment()
                MyScreenState.SECOND -> SecondFragment()
            }.withFlowScreen(screen)
    }
}
