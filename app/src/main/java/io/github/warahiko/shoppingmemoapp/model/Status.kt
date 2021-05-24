package io.github.warahiko.shoppingmemoapp.model

enum class Status(private val text: String) {
    NEW("New"),
    DONE("Done"),
    ARCHIVED("Archived"),
    DELETED("Deleted");

    companion object {
        fun from(text: String) = values().single { it.text == text }
    }
}
