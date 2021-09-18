package io.github.warahiko.shoppingmemoapp.ui.common.ext

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow

fun Job.withLoading(isLoading: MutableStateFlow<Boolean>): Job {
    isLoading.value = true
    return this.apply {
        invokeOnCompletion { isLoading.value = false }
    }
}
