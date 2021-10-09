package io.github.warahiko.shoppingmemoapp.usecase.tag

import io.github.warahiko.shoppingmemoapp.data.model.Tag
import io.github.warahiko.shoppingmemoapp.data.repository.TagListRepository
import javax.inject.Inject

class AddTagUseCase @Inject constructor(
    private val tagListRepository: TagListRepository,
) {
    suspend operator fun invoke(tag: Tag) {
        tagListRepository.addTag(tag)
    }
}
