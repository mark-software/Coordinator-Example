package com.hopskipdrive.coordinatorexample.domain

/**
 * Created by Mark Miller.
 */
class DefaultDoSomethingUseCase: DoSomethingUseCase {

    override fun invoke(): String {
        return "Hello World"
    }
}
