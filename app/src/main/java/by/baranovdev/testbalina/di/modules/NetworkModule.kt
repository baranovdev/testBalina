package by.baranovdev.testbalina.di.modules

import by.baranovdev.testbalina.data.remote.service.CommentApi
import by.baranovdev.testbalina.data.remote.service.ImageApi
import by.baranovdev.testbalina.data.remote.service.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    fun provideUserApi(
        retrofit: Retrofit
    ):UserApi = retrofit.create(UserApi::class.java)

    @Provides
    fun provideImageApi(
        retrofit: Retrofit
    ):ImageApi = retrofit.create(ImageApi::class.java)

    @Provides
    fun provideCommentApi(
        retrofit: Retrofit
    ):CommentApi = retrofit.create(CommentApi::class.java)

}