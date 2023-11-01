package by.baranovdev.testbalina.data.model.mapper

import by.baranovdev.testbalina.data.model.data.Comment
import by.baranovdev.testbalina.data.remote.dto.base.ListResponse
import by.baranovdev.testbalina.data.remote.dto.base.Response
import by.baranovdev.testbalina.data.remote.dto.comment.CommentResponse

class CommentMapper : ListMapper<Comment, CommentResponse> {
    fun mapCommentsFromNetworkToUi(
        response: ListResponse<CommentResponse>,
        imageId: Int
    ): List<Comment> =
        response.data?.let {list ->
            list.map { response ->
                Comment(
                    date = response.date,
                    id = response.id,
                    imageId = imageId,
                    text = response.text
                )
            }
        } ?: emptyList()

    override fun mapFromNetworkToUi(response: ListResponse<CommentResponse>): List<Comment> =
        mapCommentsFromNetworkToUi(response, 0)


}