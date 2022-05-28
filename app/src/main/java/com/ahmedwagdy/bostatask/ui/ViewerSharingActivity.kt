package com.ahmedwagdy.bostatask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahmedwagdy.bostatask.databinding.ActivityProfileBinding
import com.ahmedwagdy.bostatask.databinding.ActivityViewerSharingBinding
import com.ahmedwagdy.bostatask.models.PhotoModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grid_image_card.view.*

class ViewerSharingActivity : AppCompatActivity() {

    companion object{
        const val PHOTO_OBJ = "PHOTO_OBJ"
    }
    private lateinit var binding: ActivityViewerSharingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewerSharingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extra = intent.extras
        if (extra != null) {
            val photo = extra.get(PHOTO_OBJ) as PhotoModel?
            binding.defaultAppBar.tvTitle.text = photo?.title?:""
            photo?.url?.let { loadPhoto(it)}
        }
    }

    private fun loadPhoto(url:String){
     /*   Picasso.get()
            .load(url)
            .fit()
            .centerCrop()
            .into()*/
    }

}