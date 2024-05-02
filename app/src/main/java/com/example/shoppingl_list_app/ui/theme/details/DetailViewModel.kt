package com.example.shoppingl_list_app.ui.theme.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingl_list_app.Category
import com.example.shoppingl_list_app.Graph
import com.example.shoppingl_list_app.Utils
import com.example.shoppingl_list_app.models.Item
import com.example.shoppingl_list_app.models.ShoppingList
import com.example.shoppingl_list_app.models.Store
import com.example.shoppingl_list_app.ui.theme.repository.Repository
import kotlinx.coroutines.launch
import java.util.Date


class DetailViewModel
    constructor(
        private val itemId:Int,
        private val  repository: Repository = Graph.repository
    ) : ViewModel() {

    var state by mutableStateOf(DetailState())
        private set
    val isFieldsNotEmpty: Boolean
        get() = state.item.isNotEmpty() &&
                state.store.isNotEmpty() &&
                state.qty.isNotEmpty()

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
                Item(itemName = state.item,
                    listId = state.category.id,
                    date = state.date,
                    quantity = state.qty,
                    storeIdFk = state.storeList.find {
                        it.storeName == state.store
                    }?.id ?: 0,
                    isChecked = false)
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


    data class DetailState(
        val storeList: List<Store> = emptyList(),
        val item: String = "",
        val store: String = "",
        val date: Date = Date(),
        val qty: String = "",
        val isScreenDialogDismissed: Boolean = true,
        val isUpdatingItem: Boolean = false,
        val category: Category = Category(),
    )
}