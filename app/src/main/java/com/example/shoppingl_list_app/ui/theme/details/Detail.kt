package com.example.shoppingl_list_app.ui.theme.details

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DetailScreen(
    id:Int,
    navigateUp: () -> Unit,
){
    val viewModel = viewModel<DetailViewModel>(factory = DetailViewModel.DetailViewModelFactor(id))
 Scaffold {

 }

}

