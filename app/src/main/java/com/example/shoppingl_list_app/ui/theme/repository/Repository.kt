package com.example.shoppingl_list_app.ui.theme.repository

import com.example.shoppingl_list_app.models.Item
import com.example.shoppingl_list_app.models.ShoppingList
import com.example.shoppingl_list_app.models.Store
import com.example.shoppingl_list_app.data.room.ItemDao
import com.example.shoppingl_list_app.data.room.ListDao
import com.example.shoppingl_list_app.data.room.StoreDao

class Repository (
    private val listDao: ListDao,
    private val storeDao: StoreDao,
    private val itemDao: ItemDao
) {
    val store = storeDao.getAllStores()
    val getItemsWithListAndStore = listDao.getItemsWithStoreAndList()

    fun getItemWithListAndStore(id: Int) = listDao
        .getItemWithStoreAndListFilteredById(id)

    fun getItemWithStoreAndListFilteredById(id: Int) =
        listDao.getItemWithStoreAndListFilteredById(id)

    suspend fun insertList(shoppingList: ShoppingList) {
        listDao.insertShoppingList(shoppingList)

    }
    suspend fun insertStore(store: Store){
        storeDao.insert(store)
    }

    suspend fun insertItem(item: Item){
        itemDao.insert(item)
    }
    suspend fun deleteItem(item: Item){
        itemDao.delete(item)
    }
    suspend fun updateItem(item: Item){
        itemDao.update(item)
    }




}