package uz.turgunboyevjurabek.sharedanimatedapp.core.di

import org.koin.dsl.module
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.use_case.DeleteItemUseCase
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.use_case.GetAllItemUseCase
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.use_case.InsertItemUseCase
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.use_case.UpdateItemUseCase

val useCaseModule = module {
    single { DeleteItemUseCase(get()) }
    single { InsertItemUseCase(get()) }
    single { UpdateItemUseCase(get()) }
    single { GetAllItemUseCase(get()) }
}
