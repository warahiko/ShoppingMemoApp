package io.github.warahiko.shoppingmemoapp.data.ext

import io.github.warahiko.shoppingmemoapp.data.network.model.RichText
import io.github.warahiko.shoppingmemoapp.data.network.model.Text

fun List<RichText>.concatText() = fold("") { acc, richText ->
    acc + richText.text.content
}

fun String.toRichTextList(): List<RichText> = listOf(RichText(Text(this)))
