package by.baranovdev.testbalina.data.model.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import by.baranovdev.testbalina.data.model.data.Image

@Dao
interface ImageDao {
    @Query("SELECT * FROM image_table_name")
    fun getImages(): LiveData<List<Image>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImage(image: Image)

    @Query("SELECT * FROM image_table_name ORDER BY image_id DESC LIMIT :pageSize OFFSET (:pageNumber-1)*:pageSize")
    suspend fun getImagePage(pageNumber: Int, pageSize: Int): List<Image>

    @Query("SELECT * FROM image_table_name WHERE image_id= :imageId LIMIT 1")
    suspend fun getImageById(imageId: Int): Image

    @Delete
    fun deleteImage(image: Image)

}