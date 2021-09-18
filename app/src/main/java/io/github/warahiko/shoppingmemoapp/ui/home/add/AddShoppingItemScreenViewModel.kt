package io.github.warahiko.shoppingmemoapp.ui.home.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.data.model.Tag
import io.github.warahiko.shoppingmemoapp.data.repository.TagListRepository
import io.github.warahiko.shoppingmemoapp.error.LaunchSafe
import io.github.warahiko.shoppingmemoapp.ui.common.ext.withLoading
import io.github.warahiko.shoppingmemoapp.usecase.AddShoppingItemUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AddShoppingItemScreenViewModel @Inject constructor(
    tagListRepository: TagListRepository,
    private val addShoppingItemUseCase: AddShoppingItemUseCase,
    launchSafe: LaunchSafe,
) : ViewModel(), LaunchSafe by launchSafe {

    val tagsGroupedByType: StateFlow<Map<String, List<Tag>>> =
        tagListRepository
            .tagsGroupedByType
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), emptyMap())

    private val _showProgress = MutableStateFlow(false)
    val showProgress: StateFlow<Boolean> get() = _showProgress

    fun addShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launchSafe {
        addShoppingItemUseCase(shoppingItem)
    }.withLoading(_showProgress)
}
