package com.hopskipdrive.coordinatorexample.extensions

/**
 * Created by Mark Miller.
 */

//Stack-like extensions
fun <T> ArrayDeque<T>.push(element: T) = addLast(element)

fun <T> ArrayDeque<T>.pop() = removeLastOrNull()

fun <T> ArrayDeque<T>.peek() = lastOrNull()

