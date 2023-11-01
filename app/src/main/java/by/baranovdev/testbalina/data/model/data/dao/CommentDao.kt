package by.baranovdev.testbalina.data.model.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import by.baranovdev.testbalina.data.model.data.Comment
import by.baranovdev.testbalina.data.model.data.Image

@Dao
interface CommentDao {

    @Insert
    fun insertComment(comment: Comment)

    @Query("SELECT * FROM comment_table WHERE comment_image_id= :imageId")
    fun getCommentsLiveData(imageId: Int): LiveData<List<Comment>>

    @Query("SELECT * FROM comment_table WHERE comment_image_id= :imageId")
    fun getComments(imageId: Int): List<Comment>

    @Query("SELECT * FROM comment_table WHERE comment_image_id= :imageId ORDER BY comment_date DESC LIMIT :pageSize OFFSET (:pageNumber-1)*:pageSize")
    suspend fun getCommentsPage(imageId: Int, pageNumber: Int, pageSize: Int): List<Comment>

    @Delete
    suspend fun deleteComment(comment: Comment)

}