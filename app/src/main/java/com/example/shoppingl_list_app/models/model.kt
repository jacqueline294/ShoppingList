package com.example.shoppingl_list_app.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "shopping_List")
data class ShoppingList(
    @ColumnInfo(name = "List_id")
    @PrimaryKey
    val id : Int,
    val name : String
)
@Entity(tableName = "items")
data class  Item(
    @ColumnInfo(name = "item_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val item : String,
    val quantity : String,
    val listId: Int,
    val storeIdFk: Int,
    val date: Date,
    val isChecked: Boolean,
    val itemName: String
) {

}

@Entity(tableName = "stores")
data class Store(
    @ColumnInfo(name = "store_id")
    @PrimaryKey
    val id: Int =0,
    val listIdFk: Int,
    val storeName: String
)