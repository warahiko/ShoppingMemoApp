package io.github.warahiko.shoppingmemoapp.ui

class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? = if (hasBeenHandled) {
        null
    } else {
        hasBeenHandled = true
        content
    }

    fun peekContent(): T = content
}

fun <T : Any> T.toEvent(): Event<T> = Event(this)
