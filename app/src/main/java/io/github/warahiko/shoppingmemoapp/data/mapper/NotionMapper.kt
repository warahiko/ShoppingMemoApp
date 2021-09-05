package io.github.warahiko.shoppingmemoapp.data.mapper

import io.github.warahiko.shoppingmemoapp.data.network.model.RichText
import io.github.warahiko.shoppingmemoapp.data.network.model.Text

fun String.toRichTextList(): List<RichText> {
    return listOf(RichText(Text(this)))
}
