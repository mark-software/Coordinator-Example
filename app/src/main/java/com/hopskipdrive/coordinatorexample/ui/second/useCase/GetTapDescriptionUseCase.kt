package com.hopskipdrive.coordinatorexample.ui.second.useCase

import com.hopskipdrive.coordinatorexample.ui.second.SecondViewModel

/**
 * Created by Mark Miller.
 */
interface GetTapDescriptionUseCase {
    operator fun invoke(state: SecondViewModel.SecondSampleState): String
}

class DefaultGetTapDescriptionUseCase : GetTapDescriptionUseCase {
    override fun invoke(state: SecondViewModel.SecondSampleState): String {
        val result = if (state.clickedDialogButton) "Yes!" else "No!"

        return "Did we click the dialog? $result"
    }
}
