package io.github.warahiko.shoppingmemoapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.data.model.Tag
import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import io.github.warahiko.shoppingmemoapp.data.repository.TagListRepository
import io.github.warahiko.shoppingmemoapp.error.LaunchSafe
import io.github.warahiko.shoppingmemoapp.usecase.AddShoppingItemUseCase
import io.github.warahiko.shoppingmemoapp.usecase.ArchiveShoppingItemUseCase
import io.github.warahiko.shoppingmemoapp.usecase.ChangeShoppingItemIsDoneUseCase
import io.github.warahiko.shoppingmemoapp.usecase.DeleteShoppingItemUseCase
import io.github.warahiko.shoppingmemoapp.usecase.EditShoppingItemUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    tagListRepository: TagListRepository,
    shoppingListRepository: ShoppingListRepository,
    private val addShoppingItemUseCase: AddShoppingItemUseCase,
    private val changeShoppingItemIsDoneUseCase: ChangeShoppingItemIsDoneUseCase,
    private val editShoppingItemUseCase: EditShoppingItemUseCase,
    private val archiveShoppingItemUseCase: ArchiveShoppingItemUseCase,
    private val deleteShoppingItemUseCase: DeleteShoppingItemUseCase,
    launchSafe: LaunchSafe,
) : ViewModel(), LaunchSafe by launchSafe {

    val shoppingListFlow: StateFlow<Map<String, List<ShoppingItem>>> =
        shoppingListRepository.shoppingList.map { list ->
            list?.groupBy { it.tag?.type.orEmpty() }
                ?.toSortedMap()
                ?.mapValues { map ->
                    map.value.sortedBy { it.name }
                }
                ?: emptyMap()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), emptyMap())

    val tagMapFlow: StateFlow<Map<String, List<Tag>>> = tagListRepository.tagList.map { list ->
        list?.groupBy { it.type }
            ?.toSortedMap()
            ?.mapValues { map ->
                map.value.sortedBy { it.name }
            }
            ?: emptyMap()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), emptyMap())

    fun addShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launchSafe {
        addShoppingItemUseCase(shoppingItem)
    }

    fun changeShoppingItemIsDone(shoppingItem: ShoppingItem) =
        viewModelScope.launchSafe {
            changeShoppingItemIsDoneUseCase(shoppingItem, !shoppingItem.isDone)
        }

    fun editShoppingItem(newShoppingItem: ShoppingItem) = viewModelScope.launchSafe {
        editShoppingItemUseCase(newShoppingItem)
    }

    fun archiveShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launchSafe {
        archiveShoppingItemUseCase(shoppingItem)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launchSafe {
        deleteShoppingItemUseCase(shoppingItem)
    }

    fun archiveAllDone() = viewModelScope.launchSafe {
        val doneList = shoppingListFlow.value.values.flatten().filter { it.isDone }
        archiveShoppingItemUseCase(*doneList.toTypedArray())
    }
}
