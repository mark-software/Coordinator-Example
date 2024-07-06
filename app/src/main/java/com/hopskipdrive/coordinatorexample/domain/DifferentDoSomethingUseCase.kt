package com.hopskipdrive.coordinatorexample.domain

/**
 * Created by Mark Miller.
 */
class DifferentDoSomethingUseCase: DoSomethingUseCase {

    override fun invoke(): String {
        return "Testing 123"
    }
}
