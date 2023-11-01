package by.baranovdev.testbalina.ui.photos

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import com.google.android.gms.location.LocationRequest
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import by.baranovdev.testbalina.R
import by.baranovdev.testbalina.databinding.FragmentPhotosBinding
import by.baranovdev.testbalina.ui.photos.adapters.ImageItemAdapter
import by.baranovdev.testbalina.utils.BaseUtils.ifTrue
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@Suppress("DEPRECATION")
@AndroidEntryPoint
class ImagesFragment @Inject constructor() : Fragment(), ImageItemAdapter.OnImageClickListener {

    private var _binding: FragmentPhotosBinding? = null

    private val viewModel: ImagesViewModel by viewModels()

    private val locationClient by lazy {
        LocationServices
            .getFusedLocationProviderClient(
                requireActivity()
            )
    }

    private val imageAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ImageItemAdapter(this)
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result?.data != null) {
                        viewModel.sendImage(result.data?.extras?.get("data") as Bitmap)
                }
            }
        }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isLocationPermissionGranted().ifTrue {
            getCurrentLocation()
        }

        binding.rvImages.adapter = imageAdapter.apply {
            addLoadStateListener { state ->
                binding.rvImages.isVisible = state.refresh != LoadState.Loading
                binding.progressBarHome.isVisible = state.refresh == LoadState.Loading
            }
        }
        binding.layoutSwipeRefreshHome.setOnRefreshListener {
            binding.layoutSwipeRefreshHome.isRefreshing = false
            viewModel.refreshImages()
        }
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.imagesFlow.collectLatest(imageAdapter::submitData)
            }
        }
        binding.fab.setOnClickListener {
            isLocationPermissionGranted().ifTrue {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                resultLauncher.launch(cameraIntent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun isLocationPermissionGranted(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_REQUEST_CODE
            )
            false
        } else {
            true
        }
    }

    override fun onImageClick(imageId: Int) {
        findNavController().navigate(
            ImagesFragmentDirections.actionNavPhotosToNavImageDetails(imageId = imageId)
        )
    }

    override fun onImageLongClick(imageId: Int) {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(R.string.dialog_confirm_action)
                .setPositiveButton(R.string.dialog_confirm) { _, _ ->
                    viewModel.deleteImage(imageId)
                }
                .setNegativeButton(R.string.dialog_cancel) { _, _ ->
                    //Just do nothing
                }
                .show()
        }

    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val locationManager = activity?.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager
        if (locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER
            )
            || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        ) {
            locationClient.lastLocation.addOnCompleteListener { task ->
                val location = task.result
                if (location != null) {
                    viewModel.setCurrentLocation(location)
                } else {
                    val locationRequest: LocationRequest = LocationRequest()
                        .setPriority(
                            LocationRequest.PRIORITY_HIGH_ACCURACY
                        )
                        .setInterval(10000)
                        .setFastestInterval(
                            1000
                        )
                        .setNumUpdates(1)
                    val locationCallback: LocationCallback = object : LocationCallback() {
                        override fun onLocationResult(
                            locationResult: LocationResult
                        ) {
                            locationResult.lastLocation?.let {
                                viewModel.setCurrentLocation(it)
                            }
                        }
                    }

                    locationClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        Looper.myLooper()
                    )
                }
            }
        } else {
            startActivity(
                Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS
                )
                    .setFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK
                    )
            )
        }
    }

    companion object {
        const val LOCATION_REQUEST_CODE = 100
    }
}