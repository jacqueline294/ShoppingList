package com.example.shoppingl_list_app.Composables

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.shoppingl_list_app.data.room.ItemsWithStoreAndList
import com.example.shoppingl_list_app.models.Item
import java.util.Date
import java.util.Locale


@Composable
fun ShoppingItems(
    item: ItemsWithStoreAndList,
    isChecked:Boolean,
    onCheckedChange: (Item) -> Unit,
    onItemClick:() -> Unit,
){
    Card(
       modifier = Modifier
           .fillMaxWidth()
           .clickable {
               onItemClick.invoke()
           }
           .padding(8.dp)
    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ){
            Column (modifier = Modifier.padding(8.dp)){
                Text(text = item.item.itemName,
                    style = MaterialTheme.typography.bodyMedium,
                   fontWeight = FontWeight.Bold

                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = item.store.storeName)
                Spacer(modifier = Modifier.size(4.dp))

                    Text(
                        text = formatDate(item.item.date),
                        style = MaterialTheme.typography.titleSmall

                    )

                }
            Column (modifier = Modifier.padding(8.dp)){
                Text(text ="Qty: ${item.item.quantity}",
                    style =MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(4.dp))
                Checkbox(checked = isChecked,
                    onCheckedChange = { onCheckedChange.invoke(item.item) }
                )
            }

            }

        }

    }

 fun formatDate(date: Date): String =
    SimpleDateFormat("yyy-mm-dd", Locale.getDefault()).format(date)