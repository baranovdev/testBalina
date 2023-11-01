package by.baranovdev.testbalina.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import by.baranovdev.testbalina.application.TestApplication
import by.baranovdev.testbalina.data.model.data.dao.CommentDao
import by.baranovdev.testbalina.data.model.data.dao.ImageDao
import by.baranovdev.testbalina.data.model.data.dao.UserDao
import by.baranovdev.testbalina.database.AppDatabase
import by.baranovdev.testbalina.database.repository.UserLocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule() {

    @Singleton
    @Provides
    fun provideUserLocalRepository(userDao: UserDao): UserLocalRepository = UserLocalRepository(userDao)

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Singleton
    @Provides
    fun provideCommentDao(db: AppDatabase): CommentDao = db.commentDao()

    @Singleton
    @Provides
    fun provideImageDao(db: AppDatabase): ImageDao = db.imageDao()

    @Singleton
    @Provides
    fun provideAppDatabase(app: TestApplication):AppDatabase = Room.databaseBuilder(app, AppDatabase::class.java, "app_database")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideTestApplication(@ApplicationContext context: Context): TestApplication = context as TestApplication

}