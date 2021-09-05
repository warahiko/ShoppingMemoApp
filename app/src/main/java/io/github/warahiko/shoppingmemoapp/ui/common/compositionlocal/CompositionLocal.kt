package io.github.warahiko.shoppingmemoapp.ui.common.compositionlocal

import androidx.compose.runtime.compositionLocalOf
import io.github.warahiko.shoppingmemoapp.data.model.Tag

val LocalTagMap = compositionLocalOf<Map<String, List<Tag>>> { error("No data provided.") }

val LocalTypeList = compositionLocalOf<List<String>> { error("No data provided.") }
