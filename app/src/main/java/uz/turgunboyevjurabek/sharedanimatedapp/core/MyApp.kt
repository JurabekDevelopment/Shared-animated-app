package uz.turgunboyevjurabek.sharedanimatedapp.core

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import uz.turgunboyevjurabek.sharedanimatedapp.core.di.localModule
import uz.turgunboyevjurabek.sharedanimatedapp.core.di.repositoryModule
import uz.turgunboyevjurabek.sharedanimatedapp.core.di.useCaseModule
import uz.turgunboyevjurabek.sharedanimatedapp.core.di.viewModelModule

class MyApp:Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(localModule, viewModelModule, repositoryModule, useCaseModule)
            )
        }
    }

//    override fun newImageLoader(): ImageLoader {
//
//        return ImageLoader(this).newBuilder()
//            .memoryCachePolicy(CachePolicy.ENABLED)
//            .memoryCache {
//                MemoryCache.Builder(this)
//                    .maxSizePercent(0.1)
//                    .strongReferencesEnabled(true)
//                    .build()
//            }
//            .diskCachePolicy(CachePolicy.ENABLED)
//            .diskCache {
//                DiskCache.Builder()
//                    .maxSizePercent(0.03)
//                    .directory(cacheDir)
//                    .build()
//            }
//            .logger(DebugLogger())
//            .build()
//
//    }
}