package uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.use_case

import kotlinx.coroutines.flow.Flow
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.madels.Item
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.repository.data_base_repository.DataBaseRepository

class GetAllItemUseCase(private val dataBaseRepository: DataBaseRepository) {
      suspend  operator  fun invoke():Flow<List<Item>>{
        return  dataBaseRepository.getAllItems()
    }
}