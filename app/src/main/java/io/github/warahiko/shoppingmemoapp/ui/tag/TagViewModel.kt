package io.github.warahiko.shoppingmemoapp.ui.tag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.warahiko.shoppingmemoapp.data.model.Tag
import io.github.warahiko.shoppingmemoapp.data.repository.TagListRepository
import io.github.warahiko.shoppingmemoapp.error.LaunchSafe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor(
    tagListRepository: TagListRepository,
    launchSafe: LaunchSafe,
) : ViewModel(), LaunchSafe by launchSafe {

    val tags: StateFlow<List<Tag>> = tagListRepository.tagList.map {
        it ?: emptyList()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), emptyList())

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing
}
