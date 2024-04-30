package com.example.shoppingl_list_app

import android.app.Application

class ShoppingApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}