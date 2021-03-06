package io.github.warahiko.shoppingmemoapp.ui.tag.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.warahiko.shoppingmemoapp.data.model.Tag
import io.github.warahiko.shoppingmemoapp.data.repository.TagListRepository
import io.github.warahiko.shoppingmemoapp.error.LaunchSafe
import io.github.warahiko.shoppingmemoapp.ui.common.ext.withLoading
import io.github.warahiko.shoppingmemoapp.usecase.tag.AddTagUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AddTagScreenViewModel @Inject constructor(
    tagListRepository: TagListRepository,
    private val addTagUseCase: AddTagUseCase,
    launchSafe: LaunchSafe,
) : ViewModel(), LaunchSafe by launchSafe {

    val types: StateFlow<List<String>> =
        tagListRepository
            .types
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), emptyList())

    private val _showProgress = MutableStateFlow(false)
    val showProgress: StateFlow<Boolean> get() = _showProgress

    fun addTag(tag: Tag) = viewModelScope.launchSafe {
        addTagUseCase(tag)
    }.withLoading(_showProgress)
}
