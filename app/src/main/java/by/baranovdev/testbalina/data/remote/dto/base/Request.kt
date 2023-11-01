package by.baranovdev.testbalina.data.remote.dto.base

data class Request<R>(
    val body: R,
    val status: Status
)