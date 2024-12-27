package uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.use_case

import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.madels.Item
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.repository.data_base_repository.DataBaseRepository

class UpdateItemUseCase(private val dataBaseRepository: DataBaseRepository) {
    suspend operator fun invoke(item: Item){
        dataBaseRepository.updateItem(item)
    }
}