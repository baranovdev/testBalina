package by.baranovdev.testbalina.data.model.mapper

import by.baranovdev.testbalina.data.model.data.UiModel
import by.baranovdev.testbalina.data.remote.dto.base.ListResponse
import by.baranovdev.testbalina.data.remote.dto.base.Response
import by.baranovdev.testbalina.data.remote.dto.image.ImageRequest

interface Mapper<T: UiModel, R> {
    fun mapFromNetworkToUi(response: Response<R>): T
}

interface ListMapper<T: UiModel, R> {
    fun mapFromNetworkToUi(response: ListResponse<R>): List<T>
}