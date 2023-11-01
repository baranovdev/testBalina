package by.baranovdev.testbalina.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.baranovdev.testbalina.data.model.data.Image
import by.baranovdev.testbalina.database.repository.ImageLocalRepository
import by.baranovdev.testbalina.network.repository.ImageNetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val imageLocalRepository: ImageLocalRepository
) : ViewModel() {

    val imagesLiveData: LiveData<List<Image>> = imageLocalRepository.getImageListLiveData()

}