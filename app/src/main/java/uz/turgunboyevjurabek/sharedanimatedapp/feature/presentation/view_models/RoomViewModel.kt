package uz.turgunboyevjurabek.sharedanimatedapp.feature.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.sharedanimatedapp.core.utils.MyResult
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.madels.Item
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.use_case.DeleteItemUseCase
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.use_case.GetAllItemUseCase
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.use_case.InsertItemUseCase
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.use_case.UpdateItemUseCase

class RoomViewModel(
    private val getAllItemUseCase: GetAllItemUseCase,
    private val insertItemUseCase: InsertItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val updateItemUseCase: UpdateItemUseCase
):ViewModel() {

    private val _getAllItems = MutableStateFlow<MyResult<List<Item>>>(MyResult.idle())
    val getAllItems = _getAllItems.asStateFlow()

    init {
        viewModelScope.launch {
            getAllItems()
        }
    }

    private suspend fun getAllItems(){
            _getAllItems.value = MyResult.loading("Loading")
            try {
                getAllItemUseCase().collect {
                    _getAllItems.value.data = it
                }
                _getAllItems.value = MyResult.success(_getAllItems.value.data!!)

            }catch (e:Exception){
                _getAllItems.value = MyResult.error(e.message.toString())
                Log.d("Error at ViewModel", e.message.toString())
            }
        }

    fun insertItem(item: Item){
        viewModelScope.launch {
            insertItemUseCase(item)
        }
    }


}