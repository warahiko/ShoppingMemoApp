package io.github.warahiko.shoppingmemoapp.data.network.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

@Serializable
data class Property(
    val title: List<RichText>? = null,
    val number: Long? = null,
    val select: Select? = null,
    val date: Date? = null,
    @SerialName("rich_text") val richTexts: List<RichText>? = null,
    @SerialName("checkbox") val isChecked: Boolean? = null,
    @SerialName("relation") val relations: List<Relation>? = null,
)

@Serializable
data class RichText(
    val text: Text,
)

@Serializable
data class Text(
    val content: String,
)

@Serializable
data class Select(
    val name: String,
)

@Serializable(with = Date.DateSerializer::class)
data class Date(
    val start: String,
) {
    object DateSerializer : KSerializer<Date> {
        override val descriptor: SerialDescriptor
            get() = buildClassSerialDescriptor(Date::class.qualifiedName!!) {
                element<String>("start")
            }

        override fun serialize(encoder: Encoder, value: Date) {
            if (value.start.isBlank()) {
                encoder.encodeNull()
                return
            }
            encoder.encodeStructure(descriptor) {
                encodeStringElement(descriptor, 0, value.start)
            }
        }

        override fun deserialize(decoder: Decoder): Date {
            var start: String? = null
            return decoder.decodeStructure(descriptor) {
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> {
                            start = decodeStringElement(descriptor, index)
                        }
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                Date(start = requireNotNull(start))
            }
        }
    }
}

@Serializable
data class Relation(
    val id: String,
)
