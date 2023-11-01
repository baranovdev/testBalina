package by.baranovdev.testbalina.database.repository

import androidx.lifecycle.LiveData
import by.baranovdev.testbalina.data.model.data.Comment
import by.baranovdev.testbalina.data.model.data.dao.CommentDao
import javax.inject.Inject

class CommentLocalRepository @Inject constructor(
    private val commentDao: CommentDao
) {

    fun addComment(comment: Comment) = commentDao.insertComment(comment)

    fun getCommentsByImageId(imageId: Int): LiveData<List<Comment>> = commentDao.getCommentsLiveData(imageId)

    suspend fun getCommentsPage(imageId: Int, page: Int, size: Int): List<Comment> = commentDao.getCommentsPage(imageId, page, size)

    suspend fun deleteComment(comment: Comment) = commentDao.deleteComment(comment)



}