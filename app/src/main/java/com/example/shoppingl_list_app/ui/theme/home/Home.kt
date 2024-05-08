package com.example.shoppingl_list_app.ui.theme.home

import android.annotation.SuppressLint

import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppingl_list_app.Category
import com.example.shoppingl_list_app.Composables.CategoryItem
import com.example.shoppingl_list_app.Composables.ShoppingItems
import com.example.shoppingl_list_app.Utils



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(onNavigate: (Int) -> Unit) {
    val homeViewModel = viewModel(modelClass = HomeViewModel::class.java)
    val homeState = homeViewModel.state

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigate.invoke(-1) }) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = null)
            }
        }
    ) {
        LazyColumn {
            item {
                LazyRow {
                    items(Utils.category) { category: Category ->
                        CategoryItem(
                            iconRes = category.resId,
                            title = category.title,
                            selected = category === homeState.category
                        ) {
                            homeViewModel.onCategoryChange(category)
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                    }
                }
            }
            items(homeState.items) {
                ShoppingItems(
                    item = it,
                    isChecked = it.item.isChecked,
                    onCheckedChange = { isChecked ->
                        if (isChecked){
                            homeViewModel.onEvent(HomeViewModel.Event.ItemChecked(it.item))
                        } else {
                            homeViewModel.onEvent(HomeViewModel.Event.ItemUnchecked(it.item))
                        }
                    }
                ) {

                }
                }

                }
            }
        }

