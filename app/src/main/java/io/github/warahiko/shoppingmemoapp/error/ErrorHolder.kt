package io.github.warahiko.shoppingmemoapp.error

import io.github.warahiko.shoppingmemoapp.ui.Event
import io.github.warahiko.shoppingmemoapp.ui.toEvent
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorHolder @Inject constructor() {

    // `onBufferOverflow` がデフォルトの場合、tryEmit がsuspend になって失敗するときがある
    private val _error = MutableSharedFlow<Event<AppError>>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val error: SharedFlow<Event<AppError>>
        get() = _error

    // CoroutineExceptionHandler ではsuspend が呼びづらい
    fun notifyError(error: AppError) {
        check(_error.tryEmit(error.toEvent()))
    }
}
