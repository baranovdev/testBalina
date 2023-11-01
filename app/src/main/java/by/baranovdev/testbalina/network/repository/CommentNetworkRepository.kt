package by.baranovdev.testbalina.network.repository

import android.util.Log
import by.baranovdev.testbalina.data.model.data.Comment
import by.baranovdev.testbalina.data.remote.service.CommentApi
import by.baranovdev.testbalina.database.repository.UserLocalRepository
import by.baranovdev.testbalina.utils.ErrorHandler
import by.baranovdev.testbalina.utils.parseResult
import javax.inject.Inject

class CommentNetworkRepository @Inject constructor(
    private val commentApi: CommentApi,
    private val userLocalRepository: UserLocalRepository,
    private val errorHandler: ErrorHandler
) {
    suspend fun getCommentsByImageId(imageId: Int, page: Int) = commentApi.getComments(
        userLocalRepository.getUser()?.token,
        imageId,
        page
    )
//        .parseResult(errorHandler)

    suspend fun deleteComment(comment: Comment) = commentApi.deleteComment(
        token = userLocalRepository.getUser()?.token,
        imageId = comment.imageId,
        commentId = comment.id
    )

    suspend fun sendComment(text: String, imageId: Int) = commentApi.sendComment(
        token = userLocalRepository.getUser()?.token,
        imageId = imageId,
        commentText = text
    )
//        .parseResult(errorHandler)


}
