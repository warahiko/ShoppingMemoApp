package io.github.warahiko.shoppingmemoapp.ui.tag.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.warahiko.shoppingmemoapp.data.model.Tag
import io.github.warahiko.shoppingmemoapp.data.repository.TagListRepository
import io.github.warahiko.shoppingmemoapp.error.LaunchSafe
import io.github.warahiko.shoppingmemoapp.ui.common.ext.withLoading
import io.github.warahiko.shoppingmemoapp.usecase.tag.DeleteTagUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TagListScreenViewModel @Inject constructor(
    private val tagListRepository: TagListRepository,
    private val deleteTagUseCase: DeleteTagUseCase,
    launchSafe: LaunchSafe,
) : ViewModel(), LaunchSafe by launchSafe {

    val tags: StateFlow<Map<String, List<Tag>>> =
        tagListRepository
            .tagsGroupedByType
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), emptyMap())

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing

    private val _tagToDelete = MutableStateFlow<Tag?>(null)
    val tagToDelete: StateFlow<Tag?> get() = _tagToDelete

    private val _showProgress = MutableStateFlow(false)
    val showProgress: StateFlow<Boolean> get() = _showProgress

    fun fetchTags() = viewModelScope.launchSafe {
        tagListRepository.fetchTagList()
    }.withLoading(_isRefreshing)

    fun showDeleteTagConfirmationDialog(tag: Tag) {
        _tagToDelete.value = tag
    }

    fun dismissDeleteTagConfirmationDialog() {
        _tagToDelete.value = null
    }

    fun deleteTag(tag: Tag): Job {
        _tagToDelete.value = null
        return viewModelScope.launchSafe {
            deleteTagUseCase(tag)
        }.withLoading(_showProgress)
    }
}
