package by.baranovdev.testbalina.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.baranovdev.testbalina.BuildConfig
import by.baranovdev.testbalina.data.model.data.Comment
import by.baranovdev.testbalina.data.model.data.Image
import by.baranovdev.testbalina.data.model.data.User
import by.baranovdev.testbalina.data.model.data.dao.CommentDao
import by.baranovdev.testbalina.data.model.data.dao.ImageDao
import by.baranovdev.testbalina.data.model.data.dao.UserDao

@Database(
    entities = [User::class, Comment::class, Image::class],
    version = BuildConfig.DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun imageDao(): ImageDao
    abstract fun commentDao(): CommentDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}