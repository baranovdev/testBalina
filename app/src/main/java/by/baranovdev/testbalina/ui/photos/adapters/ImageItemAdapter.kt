package by.baranovdev.testbalina.ui.photos.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.paging.PositionalDataSource
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.baranovdev.testbalina.R
import by.baranovdev.testbalina.data.model.data.Image
import by.baranovdev.testbalina.databinding.ItemImageBinding
import com.bumptech.glide.Glide
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

class ImageItemAdapter(private val listener: OnImageClickListener) : PagingDataAdapter<Image, ImageItemViewHolder>(ImageDiffItemCallback) {

    interface OnImageClickListener{
        fun onImageClick(imageId: Int)
        fun onImageLongClick(imageId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItemViewHolder =
        ImageItemViewHolder(
            binding = ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
            ),
            listener = listener
        )

    override fun onBindViewHolder(holder: ImageItemViewHolder, position: Int) =
        getItem(position)?.let { holder.bind(it) } ?: Unit

}

class ImageItemViewHolder(
    val binding: ItemImageBinding,
    private val listener: ImageItemAdapter.OnImageClickListener
) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SimpleDateFormat")
    fun bind(item: Image){
        binding.tvImageTitle.text = SimpleDateFormat("dd.MM.yyyy").format(Date(TimeUnit.SECONDS.toMillis(item.date?.toLong() ?: 0)))
        Glide.with(binding.root.context).load(item.url).into(binding.ivImage)
        binding.root.setOnClickListener {
            item.id?.let{listener.onImageClick(it)}
        }
        binding.root.setOnLongClickListener {
            item.id?.let {
                listener.onImageLongClick(it)
            }
            vibratePhone()
            true
        }
    }

    private fun vibratePhone() {
        val vibrator = binding.root.context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }
}

private object ImageDiffItemCallback: DiffUtil.ItemCallback<Image>(){
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean  = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean = oldItem.url == newItem.url
}