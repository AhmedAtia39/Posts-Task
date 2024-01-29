package sa.com.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import sa.com.data.local.posts.PostEntity
import sa.com.databinding.PostItemBinding

class PostsAdapter(
    private val open: (PostEntity) -> Unit,
) : ListAdapter<PostEntity?, ViewHolder>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemVH(PostItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(holder is ItemVH)
            holder.bind(getItem(position)!!, open)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ItemVH(private val mBinding: PostItemBinding) : ViewHolder(mBinding.root) {

        fun bind(item: PostEntity, open: (PostEntity) -> Unit, ) {
            mBinding.postTitle.text = item.title

            mBinding.container.setOnClickListener { open(item) }
        }
    }

    companion object {
        val diff by lazy {
            object : DiffUtil.ItemCallback<PostEntity?>() {
                override fun areItemsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
                    return newItem.id == oldItem.id
                }
                override fun areContentsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }
}