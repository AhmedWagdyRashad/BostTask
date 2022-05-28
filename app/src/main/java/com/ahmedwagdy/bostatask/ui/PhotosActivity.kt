package com.ahmedwagdy.bostatask.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ahmedwagdy.bostatask.databinding.ActivityPhotosBinding
import com.ahmedwagdy.bostatask.models.AlbumModel
import com.ahmedwagdy.bostatask.models.PhotoModel
import com.ahmedwagdy.bostatask.network.api.NetworkResult
import com.ahmedwagdy.bostatask.ui.adapters.PhotosAdapter
import com.ahmedwagdy.bostatask.viewmodels.PhotosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotosActivity : AppCompatActivity(), TextWatcher {
    companion object{
        const val ALBUM_OBJ = "ALBUM_OBJ"
    }
    private val TAG = "PhotosActivity"
    private val photosAdapter by lazy {
        PhotosAdapter(this)
    }
    private val photosViewModel by viewModels<PhotosViewModel>()
    private val photosList = mutableListOf<PhotoModel>()
    private lateinit var binding: ActivityPhotosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchImage.addTextChangedListener(this)
        setupGridView()
        val extra = intent.extras
        if (extra != null) {
            val album = extra.get(ALBUM_OBJ) as AlbumModel?
           binding.defaultAppBar.tvTitle.text = album?.title?:""
            album?.id?.let { getPhotos(it)}
        }
    }

    private fun setupGridView(){
        photosAdapter.setOnClickListener(onItemClickListener)
        binding.photosGrid.adapter = photosAdapter
        binding.photosGrid.layoutManager = GridLayoutManager(this,3)
        //binding.photosGrid.layoutManager = LinearLayoutManager(this)
    }
    private val onItemClickListener = object : PhotosAdapter.OnItemClickListener{
        override fun onItemClick(photoModel: PhotoModel, position: Int) {
            val intent = Intent(this@PhotosActivity, ViewerSharingActivity::class.java).apply {
                putExtra(ViewerSharingActivity.PHOTO_OBJ,photoModel)
            }
            startActivity(intent)
        }

    }


    private fun getPhotos(albumId: Int){
        showProgressBar(true)
        photosViewModel.getPhotos(albumId)
        photosViewModel.photosResponse.observe(this) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        response.data?.filterNotNull()?.let {
                            photosList.clear()
                            photosList.addAll(it)
                            photosAdapter.submitList(photosList)
                        }
                        Log.i(TAG, "getPhotos: Success->> photosList==$photosList")
                        showProgressBar(false)
                    }
                    is NetworkResult.Error -> {
                        showProgressBar(false)
                        Log.i(
                            TAG,
                            "getPhotos: Error Message->> ${response.message}"
                        )

                    }
                }
            }
    }

    private fun showProgressBar(isShown: Boolean) {
        if (isShown) {
            binding.progressBar.visibility = View.VISIBLE
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            binding.progressBar.visibility = View.GONE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s.isNullOrEmpty()) {
            photosAdapter.submitList(photosList.distinct())
        } else{
            val model = ArrayList<PhotoModel>()
            photosList.map {
                if (it.title!!.toLowerCase().contains(s.toString().toLowerCase()) && s.isNotEmpty()) {
                    model.add(it)
                }
            }
            photosAdapter.submitList(model.distinct())
        }
    }

    override fun afterTextChanged(p0: Editable?) {

    }
}