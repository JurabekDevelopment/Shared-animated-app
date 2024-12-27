package uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.repository.data_base_repository

import kotlinx.coroutines.flow.Flow
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.madels.Item

interface DataBaseRepository {
    suspend fun getAllItems(): Flow<List<Item>>
    suspend fun insertItem(item: Item)
    suspend fun deleteItem(item: Item)
    suspend fun updateItem(item: Item)
}