package io.mhan.webflux

import kotlinx.coroutines.runBlocking

fun main() {

    // 비동기 동작 안함
    runBlocking {
        println("Hello")
        println(Thread.currentThread().name)
    }

    println("World")
    println(Thread.currentThread().name)
}