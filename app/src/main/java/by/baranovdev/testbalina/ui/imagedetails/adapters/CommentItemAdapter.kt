package by.baranovdev.testbalina.ui.imagedetails.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.baranovdev.testbalina.data.model.data.Comment
import by.baranovdev.testbalina.data.model.data.Image
import by.baranovdev.testbalina.databinding.ItemCommentBinding
import by.baranovdev.testbalina.databinding.ItemImageBinding
import by.baranovdev.testbalina.ui.photos.adapters.ImageItemAdapter
import by.baranovdev.testbalina.ui.photos.adapters.ImageItemViewHolder
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit
import kotlin.time.DurationUnit

class CommentItemAdapter(private val listener: OnCommentClickListener): PagingDataAdapter<Comment, CommentItemViewHolder>(CommentDiffItemCallback) {

    interface OnCommentClickListener{
        fun onCommentLongClick(comment: Comment)
    }

    override fun onBindViewHolder(holder: CommentItemViewHolder, position: Int){
        getItem(position)?.let { holder.bind(it) }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentItemViewHolder =
        CommentItemViewHolder(
        binding = ItemCommentBinding.inflate(
            LayoutInflater.from(parent.context)
        ),
        listener = listener
    )


}

class CommentItemViewHolder(
    private val binding: ItemCommentBinding,
    private val listener: CommentItemAdapter.OnCommentClickListener
) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SimpleDateFormat")
    fun bind(item: Comment){
        binding.tvCommentDate.text = SimpleDateFormat("hh:mm aaa, dd.MM.yyyy").format(Date(TimeUnit.SECONDS.toMillis(item.date?.toLong() ?: 0)))
        binding.tvComment.text = item.text
        binding.root.setOnLongClickListener {
            item.let{listener.onCommentLongClick(it)}
            true
        }
    }
}
private object CommentDiffItemCallback: DiffUtil.ItemCallback<Comment>(){
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean  = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean = oldItem.text.contentEquals(newItem.text)
}
