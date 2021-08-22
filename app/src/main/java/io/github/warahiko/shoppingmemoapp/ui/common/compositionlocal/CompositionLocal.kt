package io.github.warahiko.shoppingmemoapp.ui.common.compositionlocal

import androidx.compose.runtime.compositionLocalOf
import io.github.warahiko.shoppingmemoapp.model.Tag

val LocalTagList = compositionLocalOf<List<Tag>> { error("No data provided.") }
