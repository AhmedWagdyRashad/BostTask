package com.ahmedwagdy.bostatask.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahmedwagdy.bostatask.databinding.RecyclerItemAlbumBinding
import com.ahmedwagdy.bostatask.models.AlbumModel


class AlbumsAdapter(private val context: Context):
    RecyclerView.Adapter<AlbumsAdapter.ItemViewHolder>()  {

    private lateinit var itemClickListener: OnItemClickListener

    class ItemViewHolder(val binding: RecyclerItemAlbumBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // we can use parent.context instead of using global context
        val binding = RecyclerItemAlbumBinding.inflate(LayoutInflater.from(context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
       val item =  with(holder){
            binding.tvTitleAlbumItem
        }
        val album = differ.currentList[position]
        // check if the item is not null or empty
        if (!album.title.isNullOrEmpty())
            item.text = album.title
            item.setOnClickListener { itemClickListener.onItemClick(album, position) }
    }


    private val diffCallback = object : DiffUtil.ItemCallback<AlbumModel>() {
        override fun areItemsTheSame(oldItem: AlbumModel, newItem: AlbumModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AlbumModel, newItem: AlbumModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<AlbumModel>){
        differ.submitList(list)
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun getItemCount(): Int = differ.currentList.size

    interface OnItemClickListener{
        fun onItemClick(albumModel: AlbumModel, position: Int)
    }
}