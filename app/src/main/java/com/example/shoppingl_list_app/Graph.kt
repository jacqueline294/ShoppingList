package com.example.shoppingl_list_app

import android.content.Context
import com.example.shoppingl_list_app.data.room.ShoppingListDatabase
import com.example.shoppingl_list_app.ui.theme.repository.Repository


object Graph {
    lateinit var db: ShoppingListDatabase
        private set

    val repository by lazy {
        Repository(
            listDao = db.listDao(),
            storeDao = db.storeDao(),
            itemDao = db.itemDao()

        )
    }
    fun provide(context: Context){
        db = ShoppingListDatabase.getDatabase(context)
    }
    }

