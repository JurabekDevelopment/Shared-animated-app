package uz.turgunboyevjurabek.sharedanimatedapp.core.di

import org.koin.dsl.module
import uz.turgunboyevjurabek.sharedanimatedapp.feature.presentation.view_models.RoomViewModel

val viewModelModule = module {
    single { RoomViewModel(get(), get(), get(), get()) }
}