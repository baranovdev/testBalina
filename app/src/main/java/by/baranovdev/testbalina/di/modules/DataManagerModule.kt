package by.baranovdev.testbalina.di.modules

import by.baranovdev.testbalina.data.remote.service.ImageApi
import by.baranovdev.testbalina.database.repository.ImageLocalRepository
import by.baranovdev.testbalina.datamanager.ImageDataManager
import by.baranovdev.testbalina.network.repository.ImageNetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ActivityComponent::class)
object DataManagerModule {

    @Provides
    fun provideImageDataManager(
        imageNetworkRepository: ImageNetworkRepository,
        imageLocalRepository: ImageLocalRepository
    ): ImageDataManager = ImageDataManager(imageNetworkRepository, imageLocalRepository)
}