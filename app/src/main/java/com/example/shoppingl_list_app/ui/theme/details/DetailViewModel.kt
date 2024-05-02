package com.example.shoppingl_list_app.ui.theme.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shoppingl_list_app.Category
import com.example.shoppingl_list_app.Graph
import com.example.shoppingl_list_app.Utils
import com.example.shoppingl_list_app.models.Item
import com.example.shoppingl_list_app.models.ShoppingList
import com.example.shoppingl_list_app.models.Store
import com.example.shoppingl_list_app.ui.theme.repository.Repository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date

class DetailViewModel(
    private val itemId: Int,
    private val repository: Repository = Graph.repository
) : ViewModel() {
    var state by mutableStateOf(DetailState())  
        private set                             
    init {
        addListItem()
        getStores()
        if (itemId != -1){
            viewModelScope.launch {
                repository
                    .getItemWithStoreAndListFilteredById(itemId)
                    .collectLatest {
                        state = state.copy(
                            item.it.item.itemName,
                            store = it.store.storeName,
                            date = it.item.date,
                            category = Utils.category.find { c ->
                                c.id ==it.shoppingList.id
                            } ?: Category(),
                            qty = it.item.quantity
                        )

                    }
            }
        }
    }

    init {
       state = if (itemId != -1) {
            state.copy(isUpdatingItem = true)
        } else {
           state.copy(isUpdatingItem = false)
        }
    }


    val isFieldsNotEmpty: Boolean
        get() = state.item.isNotEmpty() &&
                state.store.isNotEmpty() &&
                state.qty.isNotEmpty()

    init {
        addListItem()
    }

    fun onItemChange(newValue: String) {
        state = state.copy(item = newValue)
    }

    fun onStoreChange(newValue: String) {
        state = state.copy(store = newValue)
    }

    fun onQtyChange(newValue: String) {
        state = state.copy(qty = newValue)
    }

    fun onDateChange(newValue: Date) {
        state = state.copy(date = newValue)
    }

    fun onCategoryChange(newValue: Category) {
        state = state.copy(category = newValue)
    }

    fun onScreenDialogDismissed(newValue: Boolean) {
        state = state.copy(isScreenDialogDismissed = newValue)
    }

    private fun addListItem() {
        viewModelScope.launch {
            Utils.category.forEach {
                repository.insertList(
                    ShoppingList(
                        id = it.id,
                        name = it.title
                    )
                )
            }
        }
    }

    fun addShoppingItem() {
        viewModelScope.launch {
            repository.insertItem(
                Item(
                    itemName = state.item,
                    listId = state.category.id,
                    date = state.date,
                    quantity = state.qty,
                    storeIdFk = state.storeList.find {
                        it.storeName == state.store
                    }?.id ?: 0,
                    isChecked = false
                )
            )
        }
    }

    fun updateShoppingItem(id: Int) {
        viewModelScope.launch {
            repository.insertItem(
                Item(
                    itemName = state.item,
                    listId = state.category.id,
                    date = state.date,
                    quantity = state.qty,
                    storeIdFk = state.storeList.find {
                        it.storeName == state.store
                    }?.id ?: 0,
                    isChecked = false,
                    id = id
                )
            )
        }
    }


    fun addStore(){
        viewModelScope.launch {
            repository.insertStore(
                Store(
                    storeName = state.store,
                    listIdFk = state.category.id
                )
            )

        }
    }

    fun getStores(){
        viewModelScope.launch {
            repository.store.collectLatest {
                state = state.copy(storeList = it)
            }
        }
    }

 class DetailViewModelFactor(  private val id : Int):ViewModelProvider.Factory{
     override fun <T : ViewModel> create(modelClass: Class<T>): T {
         return DetailViewModel(itemId = id)  as T
     }
 }



    data class DetailState(
        val storeList: List<Store> = emptyList(),
        val item: String = "",
        val store: String = "",
        val date: Date = Date(),
        val qty: String = "",
        val isScreenDialogDismissed: Boolean = true,
        val isUpdatingItem: Boolean = false,
        val category: Category = Category()
    )
}
