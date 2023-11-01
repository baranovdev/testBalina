package by.baranovdev.testbalina.application

import android.app.Application
import by.baranovdev.testbalina.BuildConfig
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(BuildConfig.YANDEX_MAPS_API_KEY)
    }
}