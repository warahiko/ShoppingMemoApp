package io.github.warahiko.shoppingmemoapp.usecase

import io.github.warahiko.shoppingmemoapp.data.repository.TagListRepository
import io.github.warahiko.shoppingmemoapp.model.Tag
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchTagListUseCase @Inject constructor(
    private val tagListRepository: TagListRepository,
) {
    suspend operator fun invoke(): Flow<List<Tag>> {
        return tagListRepository.getTagList()
    }
}
