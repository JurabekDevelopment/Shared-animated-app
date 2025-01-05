package uz.turgunboyevjurabek.sharedanimatedapp.core.di

import org.koin.dsl.module
import uz.turgunboyevjurabek.sharedanimatedapp.feature.data.repository_impl.DataBaseRepositoryImpl
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.repository.data_base_repository.DataBaseRepository

val repositoryModule = module {
    single<DataBaseRepository> { DataBaseRepositoryImpl(get()) }

}