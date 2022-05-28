package com.ahmedwagdy.bostatask.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmedwagdy.bostatask.R
import com.ahmedwagdy.bostatask.databinding.ActivityProfileBinding
import com.ahmedwagdy.bostatask.models.AlbumModel
import com.ahmedwagdy.bostatask.models.UserModel
import com.ahmedwagdy.bostatask.network.api.NetworkResult
import com.ahmedwagdy.bostatask.ui.adapters.AlbumsAdapter
import com.ahmedwagdy.bostatask.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
   private val TAG = "ProfileActivity"
    private val albumsAdapter by lazy {
        AlbumsAdapter(this)
    }
    private val profileViewModel by viewModels<ProfileViewModel>()
    private lateinit var binding: ActivityProfileBinding
    private val albumsList = mutableListOf<AlbumModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.defaultAppBar.tvTitle.text = getString(R.string.profile)
        setupRecyclerView()
        getUsers()
    }

    private fun setupRecyclerView(){
        albumsAdapter.setOnClickListener(onItemClickListener)
        binding.albumRv.adapter = albumsAdapter
        binding.albumRv.layoutManager = LinearLayoutManager(this)
    }

    private val onItemClickListener = object : AlbumsAdapter.OnItemClickListener{
        override fun onItemClick(albumModel: AlbumModel, position: Int) {
            val intent = Intent(this@ProfileActivity, PhotosActivity::class.java).apply {
                putExtra(PhotosActivity.ALBUM_OBJ,albumModel)
            }
            startActivity(intent)
        }
    }

    private fun getUsers(){
        showProgressBar(true)
        profileViewModel.getUsers()
        profileViewModel.users.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    val user = response.data?.get(0)
                    user?.let { getAlbums(it)}
                    Log.i(TAG, "user: Success->> user==$user")
                    showProgressBar(false)
                }
                is NetworkResult.Error -> {
                    showProgressBar(false)
                    Log.i(
                        TAG,
                        "getCurrentClient: Error Message->> ${response.message}"
                    )

                }
            }
        }
    }


    private fun getAlbums(userModel: UserModel){
        if (userModel.id != null) {
            profileViewModel.getAlbums(userModel.id)
            showProgressBar(true)
            profileViewModel.albums.observe(this) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        response.data?.filterNotNull()?.let {
                            albumsList.clear()
                            albumsList.addAll(it)
                            albumsAdapter.submitList(albumsList)
                            setUpUi(userModel)
                        }

                        Log.i(TAG, "getAlbums: Success->> albumsList==$albumsList")
                        showProgressBar(false)
                    }
                    is NetworkResult.Error -> {
                        showProgressBar(false)
                        Log.i(
                            TAG,
                            "getAlbums: Error Message->> ${response.message}"
                        )

                    }
                }
            }
        }
    }

    private fun setUpUi(userModel: UserModel){
        userModel.name?.let { binding.name.text = it }
        binding.address.text =
            "${userModel.address?.street?:""}, ${userModel.address?.suite?:""}, ${userModel.address?.city?:""}, \n" +
                    (userModel.address?.zipcode?:"")
        binding.myAlbumTitle.text = getString(R.string.my_album)
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

}