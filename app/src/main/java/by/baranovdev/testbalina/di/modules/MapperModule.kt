package by.baranovdev.testbalina.di.modules

import by.baranovdev.testbalina.data.model.mapper.ImageListMapper
import by.baranovdev.testbalina.data.model.mapper.ImageMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    fun provideImageListMapper(): ImageListMapper = ImageListMapper()

    @Provides
    fun provideImageMapper(): ImageMapper = ImageMapper()
}