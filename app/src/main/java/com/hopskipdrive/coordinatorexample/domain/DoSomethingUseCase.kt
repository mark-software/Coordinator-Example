package com.hopskipdrive.coordinatorexample.domain

/**
 * Created by Mark Miller.
 *
 * Swappable use case example.
 * This could also be a suspend method that performs a network request.
 */
interface DoSomethingUseCase {
    operator fun invoke(): String
}
