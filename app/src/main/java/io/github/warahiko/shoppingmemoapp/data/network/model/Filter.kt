package io.github.warahiko.shoppingmemoapp.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Filter(
    val or: List<FilterProperty>,
)

@Serializable
data class FilterProperty(
    val property: String,
    val select: FilterSelect,
)

@Serializable
data class FilterSelect(
    val equals: String,
)
