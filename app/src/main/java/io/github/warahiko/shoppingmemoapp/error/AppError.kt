package io.github.warahiko.shoppingmemoapp.error

import androidx.annotation.StringRes
import io.github.warahiko.shoppingmemoapp.R

sealed class AppError : Exception()

object NetworkError : AppError() {
    @StringRes
    val errorMessage: Int = R.string.error_network_message
}
