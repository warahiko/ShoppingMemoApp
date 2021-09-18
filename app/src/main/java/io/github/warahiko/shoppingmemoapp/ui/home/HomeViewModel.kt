package io.github.warahiko.shoppingmemoapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.warahiko.shoppingmemoapp.data.model.Tag
import io.github.warahiko.shoppingmemoapp.data.repository.TagListRepository
import io.github.warahiko.shoppingmemoapp.error.LaunchSafe
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    tagListRepository: TagListRepository,
    launchSafe: LaunchSafe,
) : ViewModel(), LaunchSafe by launchSafe {

    val tagMapFlow: StateFlow<Map<String, List<Tag>>> = tagListRepository.tagList.map { list ->
        list?.groupBy { it.type }
            ?.toSortedMap()
            ?.mapValues { map ->
                map.value.sortedBy { it.name }
            }
            ?: emptyMap()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), emptyMap())
}
