package uz.turgunboyevjurabek.sharedanimatedapp.feature.data.data_source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.madels.Item


@Dao
interface ItemDao {

    @Query("SELECT * FROM items")
      fun getAllItems(): Flow<List<ItemEntity>>

    @Insert
    suspend fun insertItem(item: ItemEntity)

    @Delete
    suspend fun deleteItem(item: ItemEntity)

    @Update
    suspend fun updateItem(item: ItemEntity)

}