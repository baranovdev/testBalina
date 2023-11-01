package by.baranovdev.testbalina.data.model.mapper

import by.baranovdev.testbalina.data.model.data.Image
import by.baranovdev.testbalina.data.remote.dto.base.ListResponse
import by.baranovdev.testbalina.data.remote.dto.image.ImageResponse

class ImageListMapper : ListMapper<Image, ImageResponse> {
    override fun mapFromNetworkToUi(response: ListResponse<ImageResponse>): List<Image> =
        response.data?.let { list ->
            list.map {
                Image(
                    date = it.date,
                    lng = it.lng,
                    id = it.id,
                    url = it.url,
                    lat = it.lat
                )
            }
        } ?: emptyList()
}