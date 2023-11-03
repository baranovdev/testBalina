package by.baranovdev.testbalina.data.remote.dto.base

import javax.xml.transform.OutputKeys

enum class Status {
    OK, ERROR_UNAUTHORIZED, ERROR_UNKNOWN, ERROR_NO_ACCESS, ERROR_NOT_FOUND, LOADING
}

fun Int.toState() = when(this){
    200 -> Status.OK
    401 -> Status.ERROR_UNAUTHORIZED
    403 -> Status.ERROR_NO_ACCESS
    404 -> Status.ERROR_NOT_FOUND
    else -> Status.ERROR_UNKNOWN
}

