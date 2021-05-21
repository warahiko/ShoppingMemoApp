package io.github.warahiko.shoppingmemoapp.data.ext

import io.github.warahiko.shoppingmemoapp.data.network.model.RichText

fun List<RichText>.concatText() = fold("") { acc, richText ->
    acc + richText.text.content
}
