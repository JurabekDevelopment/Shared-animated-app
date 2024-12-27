package uz.turgunboyevjurabek.sharedanimatedapp.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import uz.turgunboyevjurabek.sharedanimatedapp.core.di.localModule

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(
                listOf(localModule)
            )
        }
    }
}