package by.baranovdev.testbalina.di.modules

import by.baranovdev.testbalina.BuildConfig
import by.baranovdev.testbalina.database.repository.UserLocalRepository
import by.baranovdev.testbalina.network.interceptor.HeaderInterceptor
import by.baranovdev.testbalina.network.interceptor.NetworkErrorHandler
import by.baranovdev.testbalina.utils.NetworkUtils
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(gson)
            .build()

    @Provides
    fun provideCallAdapterFactory(
        userLocalRepository: UserLocalRepository
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HeaderInterceptor(userLocalRepository)
        )
        .callTimeout(NetworkUtils.CONNECTION_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
        .build()

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideGson():Gson = Gson()
}