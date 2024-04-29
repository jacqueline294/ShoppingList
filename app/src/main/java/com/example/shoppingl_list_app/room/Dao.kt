package com.example.shoppingl_list_app.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shoppingl_list_app.models.Item
import com.example.shoppingl_list_app.models.ShoppingList
import com.example.shoppingl_list_app.models.Store
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<Item>>

    @Query("SELECT * FROM items WHERE item_id = :itemId")
    fun getItem(itemId: Int): Flow<Item>
}

@Dao
interface StoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(store: Store)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(store: Store)

    @Delete
    suspend fun delete(store: Store)

    @Query("SELECT * FROM stores")
    fun getAllStores(): Flow<List<Store>>

    @Query("SELECT * FROM stores WHERE store_id = :storeId")
    fun getStore(storeId: Int): Flow<Store>
}


@Dao
interface  ListDao{
  @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertShoppingList(shoppingList: ShoppingList)


      @Query(
          """ 
    SELECT*FROM items AS I INNER JOIN shopping_List AS S
    ON I.listId= S.List_id INNER JOIN stores AS ST
    ON I.storeIdFk = ST.store_id
    """
      )

      fun getItemsWithStoreAndList():Flow<List<ItemsWithStoreAndList>>

    @Query(
        """ 
    SELECT*FROM items AS I INNER JOIN shopping_List AS S
    ON I.listId= S.List_id INNER JOIN stores AS ST
    ON I.storeIdFk = ST.store_id WHERE ST.listIdFk
    """
    )

    fun getItemWithStoreAndListFilteredById(itemId:Int)
    :Flow<List<ItemsWithStoreAndList>>

    @Query(
        """ 
    SELECT*FROM items AS I INNER JOIN shopping_List AS S
    ON I.listId= S.List_id INNER JOIN stores AS ST
    ON I.storeIdFk = ST.store_id WHERE I.item_id =:itemId
    """
    )

    fun getItemsWithStoreAndListFilteredById(itemId: Int)
            :Flow<ItemsWithStoreAndList>



}


data class ItemsWithStoreAndList(
    @Embedded val item: Item,
    @Embedded val shoppingList: ShoppingList,
    @Embedded val store: Store
)