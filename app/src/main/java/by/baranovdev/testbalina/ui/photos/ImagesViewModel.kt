package by.baranovdev.testbalina.ui.photos

import android.graphics.Bitmap
import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import by.baranovdev.testbalina.data.model.data.Image
import by.baranovdev.testbalina.data.remote.dto.image.ImageRequest
import by.baranovdev.testbalina.database.repository.ImageLocalRepository
import by.baranovdev.testbalina.network.pagingsource.ImagePagingSource
import by.baranovdev.testbalina.network.repository.ImageNetworkRepository
import by.baranovdev.testbalina.utils.ErrorHandler
import by.baranovdev.testbalina.utils.ImageUtils
import com.google.android.gms.location.LocationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val imageNetworkRepository: ImageNetworkRepository,
    private val imageLocalRepository: ImageLocalRepository,
    private val errorHandler: ErrorHandler
) : ViewModel() {

    private var currentPagingSource: ImagePagingSource? = null

    private val _currentLocation: MutableLiveData<Location> = MutableLiveData()

    val imagesFlow: StateFlow<PagingData<Image>> =
        Pager(ImagePagingSource.DEFAULT_PAGING_CONFIG) {
            ImagePagingSource(
                imageNetworkRepository,
                imageLocalRepository
            ).apply {
                currentPagingSource = this
            }
        }.flow.cachedIn(CoroutineScope(Dispatchers.IO))
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun refreshImages() {
        currentPagingSource?.invalidate()
    }

    fun sendImage(bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            val location = _currentLocation.value
            ImageUtils.encodeImage(bitmap)?.let {
                imageNetworkRepository.sendImage(
                    imageBase64 = it,
                    lat = location?.latitude ?: .0,
                    lng = location?.longitude ?: .0
                )
            }
            refreshImages()
        }
    }

    fun deleteImage(imageId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            imageNetworkRepository.deleteImage(imageId)
            refreshImages()
        }
    }

    fun setCurrentLocation(location: Location) {
        _currentLocation.value = location
    }


}