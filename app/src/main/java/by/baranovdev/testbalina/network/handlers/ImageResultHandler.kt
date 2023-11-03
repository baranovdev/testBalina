package by.baranovdev.testbalina.network.handlers

import by.baranovdev.testbalina.data.model.data.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ImageResultHandler @Inject constructor(){
    val imageStream:Flow<List<Image>> = flow {  }
}