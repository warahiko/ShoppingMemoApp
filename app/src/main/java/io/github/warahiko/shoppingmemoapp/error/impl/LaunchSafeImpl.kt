package io.github.warahiko.shoppingmemoapp.error.impl

import io.github.warahiko.shoppingmemoapp.error.ErrorCatcher
import io.github.warahiko.shoppingmemoapp.error.LaunchSafe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class LaunchSafeImpl @Inject constructor(
    private val errorCatcher: ErrorCatcher,
) : LaunchSafe {

    override fun CoroutineScope.launchSafe(
        context: CoroutineContext,
        start: CoroutineStart,
        block: suspend CoroutineScope.() -> Unit,
    ): Job = launch(context + errorCatcher.defaultPolicy, start, block)
}
