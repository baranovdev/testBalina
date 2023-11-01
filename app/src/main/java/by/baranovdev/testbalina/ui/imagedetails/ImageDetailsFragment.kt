package by.baranovdev.testbalina.ui.imagedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import by.baranovdev.testbalina.R
import by.baranovdev.testbalina.data.model.data.Comment
import by.baranovdev.testbalina.databinding.FragmentImageDetailsBinding
import by.baranovdev.testbalina.databinding.FragmentPhotosBinding
import by.baranovdev.testbalina.ui.imagedetails.adapters.CommentItemAdapter
import by.baranovdev.testbalina.ui.photos.ImagesViewModel
import by.baranovdev.testbalina.ui.photos.adapters.ImageItemAdapter
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.publishOn
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageDetailsFragment: Fragment(), CommentItemAdapter.OnCommentClickListener {

    private var _binding: FragmentImageDetailsBinding? = null

    private val viewModel: ImageDetailsViewModel by viewModels()
    private val binding get() = _binding!!

    val args: ImageDetailsFragmentArgs by navArgs()

    private val commentAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CommentItemAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setImageId()
        viewModel.imageUrl.observe(viewLifecycleOwner){ url ->
            context?.let{
                Glide.with(it).load(url).into(binding.ivImage)
            }
        }
        binding.rvComments.adapter = commentAdapter

        binding.textInputLayout.editText?.doOnTextChanged { text, _, _, _ ->
            binding.btnSendComment.isVisible = !text.isNullOrEmpty()
        }

        binding.btnSendComment.setOnClickListener {
            viewModel.sendComment(binding.textInputLayout.editText?.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCommentLongClick(comment: Comment) {
        context?.let{
            MaterialAlertDialogBuilder(it)
                .setTitle(R.string.dialog_confirm_action)
                .setPositiveButton(R.string.dialog_confirm){ _, _ ->
                    viewModel.deleteComment(comment)
                }
                .setNegativeButton(R.string.dialog_cancel){ _, _ ->
                    //Just do nothing
                }
                .show()
        }
    }

    private fun setImageId(){
        viewModel.setImageId(args.imageId)
        subscribeOnCommentFlow()
    }

    private fun subscribeOnCommentFlow(){
        viewModel.loadCommentFlow()?.let { flow ->
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                    flow.collectLatest(commentAdapter::submitData)
                }
            }
        }
    }

}