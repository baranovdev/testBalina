package by.baranovdev.testbalina.di.modules

import by.baranovdev.testbalina.data.model.data.dao.ImageDao
import by.baranovdev.testbalina.data.model.mapper.ImageListMapper
import by.baranovdev.testbalina.data.model.mapper.ImageMapper
import by.baranovdev.testbalina.data.remote.service.ImageApi
import by.baranovdev.testbalina.database.repository.ImageLocalRepository
import by.baranovdev.testbalina.database.repository.UserLocalRepository
import by.baranovdev.testbalina.network.repository.ImageNetworkRepository
import by.baranovdev.testbalina.utils.ErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object RepositoryModule {

    @Provides
    fun provideImageNetworkRepository(
        imageApi: ImageApi,
        imageListMapper: ImageListMapper,
        imageMapper: ImageMapper,
        userLocalRepository: UserLocalRepository,
        imageLocalRepository: ImageLocalRepository,
        errorHandler: ErrorHandler
    ) = ImageNetworkRepository(imageApi, imageListMapper,imageMapper, userLocalRepository,imageLocalRepository, errorHandler)
    @Provides
    fun provideImageLocalRepository(
        imageDao: ImageDao
    ) = ImageLocalRepository(imageDao)

    @Singleton
    @Provides
    fun provideErrorHandler(): ErrorHandler = ErrorHandler()
}