package uz.turgunboyevjurabek.sharedanimatedapp.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import uz.turgunboyevjurabek.sharedanimatedapp.core.di.localModule
import uz.turgunboyevjurabek.sharedanimatedapp.core.di.repositoryModule
import uz.turgunboyevjurabek.sharedanimatedapp.core.di.useCaseModule
import uz.turgunboyevjurabek.sharedanimatedapp.core.di.viewModelModule

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(localModule, viewModelModule, repositoryModule, useCaseModule)
            )
        }
    }
}