package com.example.shoppingl_list_app.ui.theme.home

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppingl_list_app.Category
import com.example.shoppingl_list_app.Utils
import com.example.shoppingl_list_app.ui.theme.theme.Shapes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(onNavigate: (Int) -> Unit) {
    val homeViewModel = viewModel(modelClass = HomeViewModel::class.java)
    val homeState = homeViewModel.state

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigate.invoke(-1) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
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
                            isSelected = { category === homeState.category },
                            onItemClick = { homeViewModel.onCategoryChange(category) }
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    @DrawableRes iconRes: Int,
    title: String,
    isSelected: () -> Boolean,
    onItemClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, start = 8.dp)
            .selectable(
                selected = isSelected(),
                interactionSource = interactionSource,
                indication = rememberRipple(),
                onClick = { onItemClick() }
            ),
        border = BorderStroke(1.dp,
            if (isSelected()) MaterialTheme.colorScheme.primary.copy(5f)
            else MaterialTheme.colorScheme.onSurface
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected()) MaterialTheme.colorScheme.primary.copy(.5f)
            else MaterialTheme.colorScheme.surface,
            contentColor = if (isSelected()) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.surface
        ),
        shape = Shapes.large
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            /*Text(text = title,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Medium)*/
        }
    }
}
