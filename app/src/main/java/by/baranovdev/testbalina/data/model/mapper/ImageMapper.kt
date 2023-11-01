package by.baranovdev.testbalina.data.model.mapper

import by.baranovdev.testbalina.data.model.data.Image
import by.baranovdev.testbalina.data.remote.dto.base.ListResponse
import by.baranovdev.testbalina.data.remote.dto.base.Response
import by.baranovdev.testbalina.data.remote.dto.image.ImageResponse

class ImageMapper: Mapper<Image, ImageResponse> {
    override fun mapFromNetworkToUi(response: Response<ImageResponse>): Image =
        response.data.let{
            Image(
                date = it?.date,
                lng = it?.lng,
                id = it?.id,
                url = it?.url,
                lat = it?.lat
            )
        }
}