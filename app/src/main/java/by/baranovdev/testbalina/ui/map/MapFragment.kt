package by.baranovdev.testbalina.ui.map

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.baranovdev.testbalina.R
import by.baranovdev.testbalina.data.model.data.Image
import by.baranovdev.testbalina.databinding.FragmentMapBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentMapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MapViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mapViewModel =
            ViewModelProvider(this).get(MapViewModel::class.java)

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        MapKitFactory.initialize(activity)

        return root
    }

    override fun onStart() {
        MapKitFactory.getInstance().onStart()
        viewModel.imagesLiveData.value?.let { initImageMarks(it) }
        viewModel.imagesLiveData.observe(viewLifecycleOwner) { images ->
            initImageMarks(images)
        }
        super.onStart()
    }

    override fun onStop() {
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    private fun initImageMarks(images: List<Image>) {
        images.map { image ->
            context?.let {
                Glide.with(it)
                    .asBitmap()
                    .load(image.url)
                    .into(object : CustomTarget<Bitmap>(SIZE_ORIGINAL, SIZE_ORIGINAL) {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            with(binding.mapView) {
                                map.mapObjects.addPlacemark().apply {
                                    geometry = Point(
                                        image.lat?.toDouble() ?: .0,
                                        image.lng?.toDouble() ?: .0
                                    )
                                    setIcon(ImageProvider.fromBitmap(resource))
                                }.addTapListener { _, _ ->
                                    findNavController().navigate(
                                        MapFragmentDirections.actionNavMapToNavImageDetails(image.id ?: 0)
                                    )
                                    true
                                }

                            }
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            TODO("Not yet implemented")
                        }


                    })
            }
        }
    }

    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null
    }
}