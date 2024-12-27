package uz.turgunboyevjurabek.sharedanimatedapp.core.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import uz.turgunboyevjurabek.sharedanimatedapp.feature.data.data_source.local.ItemDataBase

val localModule = module {

    single {
        Room.databaseBuilder(
            context = androidApplication(),
            ItemDataBase::class.java,
            "MyItemDataBase"
        ).fallbackToDestructiveMigration()
//            .allowMainThreadQueries()
            .build()
    }

    single {
        get<ItemDataBase>().itemDao()
    }


}