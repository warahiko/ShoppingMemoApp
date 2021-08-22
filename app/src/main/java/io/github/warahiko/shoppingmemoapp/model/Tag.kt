package io.github.warahiko.shoppingmemoapp.model

data class Tag(
    val name: String,
    val type: String,
) {
    override fun toString() = "$type > $name"
}
