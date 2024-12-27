package uz.turgunboyevjurabek.sharedanimatedapp.feature.data.data_source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import uz.turgunboyevjurabek.sharedanimatedapp.core.utils.MyResult

@Dao
interface ItemDao {

    @Query("SELECT * FROM items")
    suspend fun getAllItems(): MyResult<Flow<List<ItemEntity>>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ItemEntity)

    @Delete
    suspend fun deleteItem(item: ItemEntity)

    @Update
    suspend fun updateItem(item: ItemEntity)

}