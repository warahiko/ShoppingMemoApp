package io.github.warahiko.shoppingmemoapp.error

import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorCatcher @Inject constructor(
    private val errorHolder: ErrorHolder,
) {
    val defaultPolicy = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            // ネットワーク関連エラー
            is SocketException,
            is SocketTimeoutException,
            is UnknownHostException,
            is HttpException,
            -> {
                errorHolder.notifyError(NetworkError)
            }
            else -> {
                throw throwable
            }
        }
    }
}
