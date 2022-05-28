package com.ahmedwagdy.bostatask.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahmedwagdy.bostatask.databinding.GridImageCardBinding
import com.ahmedwagdy.bostatask.models.PhotoModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grid_image_card.view.*


class PhotosAdapter(private val context: Context) :
    RecyclerView.Adapter<PhotosAdapter.ItemViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    class ItemViewHolder(val binding: GridImageCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // we can use parent.context instead of using global context
        val binding = GridImageCardBinding.inflate(LayoutInflater.from(context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val photo = differ.currentList[position]
        // set item data
        holder.itemView.apply {
            card_image.setOnClickListener { itemClickListener.onItemClick(photo, position) }
            Picasso.get()
                .load(photo.url)
                .fit()
                .centerCrop()
                .into(card_image)

        }
    }


    private val diffCallback = object : DiffUtil.ItemCallback<PhotoModel>() {
        override fun areItemsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<PhotoModel>) {
        differ.submitList(list)
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun getItemCount(): Int = differ.currentList.size

    interface OnItemClickListener {
        fun onItemClick(photoModel: PhotoModel, position: Int)
    }
}