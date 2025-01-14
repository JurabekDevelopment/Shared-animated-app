@file:OptIn(ExperimentalCoroutinesApi::class)

package uz.turgunboyevjurabek.sharedanimatedapp.feature.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.serializer
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import okhttp3.internal.wait
import uz.turgunboyevjurabek.sharedanimatedapp.core.utils.MyResult
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.madels.Item
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.use_case.DeleteItemUseCase
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.use_case.GetAllItemUseCase
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.use_case.InsertItemUseCase
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.use_case.UpdateItemUseCase
import kotlin.coroutines.cancellation.CancellationException

class RoomViewModel(
    private val getAllItemUseCase: GetAllItemUseCase,
    private val insertItemUseCase: InsertItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val updateItemUseCase: UpdateItemUseCase
) : ViewModel() {


    private val _addItem = MutableStateFlow<MyResult<Item>>(MyResult.idle())
    val addItem = _addItem.asStateFlow()

    private val _updateItem = MutableStateFlow<MyResult<Item>>(MyResult.idle())
    val updateItem = _updateItem.asStateFlow()

    private val _deleteItem = MutableStateFlow<MyResult<Item>>(MyResult.idle())
    val deleteItem = _deleteItem.asStateFlow()

    private val _getAllItems = MutableStateFlow<MyResult<List<Item>>>(MyResult.idle())
    val getAllItems = _getAllItems.asStateFlow()


    init {
        CoroutineScope(Dispatchers.IO).launch {
            getAllItems()
        }
//        viewModelScope.launch {
//            getAllItemUseCase()
//                .onStart { _getAllItems.value = MyResult.loading("Loading") }
//                .catch { e -> _getAllItems.value = MyResult.error(e.message ?: "Error") }
//                .collect { items -> _getAllItems.value = MyResult.success(items) }
//        }
    }

    private suspend fun getAllItems() {

        getAllItemUseCase().onStart { _getAllItems.value = MyResult.loading("Loading") }
            .catch { e ->
                _getAllItems.value = MyResult.error(e.message ?: "Unexpected error occurred")
            }
            .collect { items ->
                _getAllItems.value = MyResult.success(items)
            }


    }


    suspend fun insertItem(item: Item) {
        _addItem.value = MyResult.loading("Loading")
        try {
            coroutineScope {
                withContext(Dispatchers.IO) {
                    insertItemUseCase(item)
                    _addItem.value = MyResult.success(item)
                }
            }
        } catch (e: Exception) {
            _addItem.value = MyResult.error(e.message ?: "An unexpected error occurred")
        }
    }

    suspend fun deleteItem(item: Item) {
        _deleteItem.value = MyResult.loading("Loading")
        try {
            coroutineScope {
                withContext(Dispatchers.IO){
                    deleteItemUseCase(item)
                    _deleteItem.value = MyResult.success(item)
                }
            }

            }catch (e:Exception){
                _deleteItem.value = MyResult.error(e.message ?: "An unexpected error occurred")

        }

    }

}