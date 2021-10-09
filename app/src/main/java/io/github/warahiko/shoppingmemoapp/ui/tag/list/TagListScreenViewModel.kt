package io.github.warahiko.shoppingmemoapp.ui.tag.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.warahiko.shoppingmemoapp.data.model.Tag
import io.github.warahiko.shoppingmemoapp.data.repository.TagListRepository
import io.github.warahiko.shoppingmemoapp.error.LaunchSafe
import io.github.warahiko.shoppingmemoapp.ui.common.ext.withLoading
import io.github.warahiko.shoppingmemoapp.usecase.tag.DeleteTagUseCase
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

    private val _deleteEvent = MutableStateFlow<DeleteEvent>(DeleteEvent.None)
    val deleteEvent: StateFlow<DeleteEvent> get() = _deleteEvent

    fun fetchTags() = viewModelScope.launchSafe {
        tagListRepository.fetchTagList()
    }.withLoading(_isRefreshing)

    fun showDeleteTagConfirmationDialog(tag: Tag) {
        _deleteEvent.value = DeleteEvent.ShowConfirmationDialog(tag)
    }

    fun dismissDeleteTagConfirmationDialog() {
        _deleteEvent.value = DeleteEvent.None
    }

    fun deleteTag(tag: Tag) = viewModelScope.launchSafe {
        _deleteEvent.value = DeleteEvent.ShowProgressDialog
        deleteTagUseCase(tag)
        _deleteEvent.value = DeleteEvent.None
    }

    sealed class DeleteEvent {
        object None : DeleteEvent()
        data class ShowConfirmationDialog(val tag: Tag) : DeleteEvent()
        object ShowProgressDialog : DeleteEvent()
    }
}
