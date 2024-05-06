package com.example.shoppingl_list_app.ui.theme.details

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppingl_list_app.Category
import com.example.shoppingl_list_app.Composambles.CategoryItem
import com.example.shoppingl_list_app.Composambles.formatDate
import com.example.shoppingl_list_app.Utils
import com.example.shoppingl_list_app.ui.theme.theme.Shapes
import java.util.Calendar
import java.util.Date
import kotlin.reflect.KFunction1

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    id: Int,
    navigateUp: () -> Unit,
) {
    val viewModel = viewModel<DetailViewModel>(factory = DetailViewModel.DetailViewModelFactory())
    Scaffold {
        DetailEntry(
            state = viewModel.state,
            onDateSelected = viewModel::onDateChange,
            onStoreChange = viewModel::onStoreChange,
            onItemChange = viewModel::onItemChange,
            onQtyChange = viewModel::onQtyChange,
            onCategoryChange = viewModel::onCategoryChange,
            onDialogDismissed = viewModel::onScreenDialogDismissed,
            onSavedStore = viewModel::addStore,
            UpdateItem = { viewModel.updateShoppingItem(id) },
            saveItem = viewModel::addShoppingItem,
            navigateUp = navigateUp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailEntry(
    modifier: Modifier = Modifier,
    state: DetailViewModel.DetailState,
    onDateSelected: (Date) -> Unit,
    onStoreChange: (String) -> Unit,
    onItemChange: (String) -> Unit,
    onQtyChange: (String) -> Unit,
    onCategoryChange: (Category) -> Unit,
    onDialogDismissed: (Boolean) -> Unit,
    onSavedStore: () -> Unit,
    UpdateItem: () -> Unit,
    saveItem: KFunction1<Int, Unit>,
    navigateUp: () -> Unit
    ){
    var isNewEnabled by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        TextField(value = state.item,
            onValueChange = {onItemChange(it)},
            label = { Text(text = "itme")},
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = Shapes.large

        )
        Spacer(modifier = Modifier.Companion.size(12.dp))

        Row {
            TextField(value = state.store,
                onValueChange = {
                    if (isNewEnabled)onStoreChange.invoke(it)
                },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                shape = Shapes.large ,
                label = { Text(text = "Store")},
                leadingIcon = {
                    Icon(imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onDialogDismissed.invoke(!state.isScreenDialogDismissed)
                        }
                    )

                }
            )
            if (!state.isScreenDialogDismissed){
                Popup (
                    onDismissRequest = {
                        onDialogDismissed.invoke(!state.isScreenDialogDismissed)
                    }
                ){
                    Surface(modifier = Modifier.padding(16.dp)) {
                        Column {
                            state.storeList.forEach {
                                Text(text =it.storeName,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clickable {
                                            onStoreChange.invoke(it.storeName)
                                            onDialogDismissed(!state.isScreenDialogDismissed)
                                        },

                                    )

                            }
                        }

                    }
                }


            }
            TextButton(onClick =  {
                isNewEnabled = if (isNewEnabled) {
                    onSavedStore.invoke()
                    !isNewEnabled
                }else{
                    !isNewEnabled
                }

            }){
                Text(text = if (isNewEnabled)"Save" else "New")
            }
        }
        Spacer(modifier = Modifier.Companion.size(12.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Row (verticalAlignment = Alignment.CenterVertically){
                Icon(imageVector = Icons.Default.DateRange,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = formatDate(state.date))
                Spacer(modifier = Modifier.size(4.dp))
                val mDatePicker= datePickerDialog(
                    context = LocalContext.current,
                    onDateSelected = {date ->
                        onDateSelected()

                    }

                )
                IconButton(onClick = { mDatePicker.show() }){
                    Icon(imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }


            }
            TextField(value = state.qty,
                onValueChange = {onQtyChange(it)},
                label = { Text(text = "Qty")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                shape = Shapes.large

            )

        }
        Spacer(modifier = Modifier.size(12.dp))
        LazyRow {
            items(Utils.category){category:Category ->
                CategoryItem(iconRes = category.resId,
                    title =category.title ,
                    selected = category== state.category
                ) {
                    onCategoryChange(category)
                }
                Spacer(modifier = Modifier.Companion.size(16.dp))
            }
        }

        val buuttonTitle = if(state.isUpdatingItem)"Update Item"
        else "Add Item"
        Button(
            onClick = {
                when(state.isUpdatingItem){
                    true -> {
                        UpdateItem.invoke()
                    }
                    false -> {
                        saveItem()
                    }
                }
                navigateUp

            },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.item.isNotEmpty()&&
                    state.store.isNotEmpty()&&
                    state.qty.isNotEmpty(),

            shape = Shapes.large
        ) {
            Text(text = buuttonTitle)

        }


    }

}

fun onDateSelected() {
    TODO()
}

fun saveItem() {
    TODO()
}

@Composable
fun datePickerDialog(
    context:Context,
    onDateSelected: (Date) -> Unit
): DatePickerDialog{
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time= Date()


    val mDatePickerDialog= DatePickerDialog(
        context,
        { _: DatePicker, mYears:Int, mMonth:Int, mDayofMonth:Int ->
            val  calendar= Calendar.getInstance()
            calendar.set(mYears,mMonth,mDayofMonth)
            onDateSelected.invoke(calendar.time)
        },year,month,day
    )

    return mDatePickerDialog
}

@Preview(showSystemUi =true)
@Composable
fun PrevDetailEntry(){
    DetailEntry(
        state = DetailViewModel.DetailState(),
        onDateSelected = {},
        onStoreChange ={},
        onItemChange = {},
        onQtyChange ={},
        onCategoryChange ={},
        onDialogDismissed ={},
        onSavedStore = {  },
        UpdateItem = { },
        saveItem = {}
    )
}

fun DetailEntry(
    state: DetailViewModel.DetailState,
    onDateSelected: (Date) -> Unit,
    onStoreChange: (String) -> Unit,
    onItemChange: (String) -> Unit,
    onQtyChange: (String) -> Unit,
    onCategoryChange: (Category) -> Unit,
    onDialogDismissed: (Boolean) -> Unit,
    onSavedStore: () -> Unit,
    UpdateItem: () -> Unit,
    saveItem: () -> Unit
) {TODO("Not yet implemented")

}
