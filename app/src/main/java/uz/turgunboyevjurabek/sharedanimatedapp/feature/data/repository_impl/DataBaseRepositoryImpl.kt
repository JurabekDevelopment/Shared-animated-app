package uz.turgunboyevjurabek.sharedanimatedapp.feature.data.repository_impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.turgunboyevjurabek.sharedanimatedapp.feature.data.data_source.local.ItemDao
import uz.turgunboyevjurabek.sharedanimatedapp.feature.data.mapper.toItem
import uz.turgunboyevjurabek.sharedanimatedapp.feature.data.mapper.toItemEntity
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.madels.Item
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.repository.data_base_repository.DataBaseRepository

class DataBaseRepositoryImpl(private val itemDao: ItemDao):DataBaseRepository {
    override suspend fun getAllItems(): Flow<List<Item>> {
        return itemDao.getAllItems().map {itemEntity ->
            itemEntity.map { it.toItem() }
        }
    }

    override suspend fun insertItem(item: Item) {
        itemDao.insertItem(item.toItemEntity())
    }

    override suspend fun deleteItem(item: Item) {
      itemDao.deleteItem(item.toItemEntity())
    }

    override suspend fun updateItem(item: Item) {
        itemDao.updateItem(item.toItemEntity())
    }
}