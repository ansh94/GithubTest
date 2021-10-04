package com.anshdeep.takehome.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.anshdeep.takehome.R
import com.anshdeep.takehome.databinding.ActivityMainBinding
import com.anshdeep.takehome.network.response.Repository
import com.anshdeep.takehome.network.response.User
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), UserRepoAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val userRepoAdapter = UserRepoAdapter(arrayListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupObservers()

        binding.repositoryList.adapter = userRepoAdapter

        binding.searchButton.setOnClickListener {
            val userId = binding.searchTextField.editText?.text.toString()
            if (userId.isNotEmpty()) {
                it.hideKeyboard()
                mainViewModel.fetchUserData(userId)
            } else {
                Snackbar.make(
                    findViewById(android.R.id.content), getString(R.string.enter_user_id),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun setupObservers() {
        mainViewModel.userLiveData.observe(this) {
            it?.let {
                updateUserData(it)
                mainViewModel.fetchUserRepoData(it.login)
            }
        }
        mainViewModel.userRepoData.observe(this) {
            it?.let {
                userRepoAdapter.replaceData(it)
                binding.repositoryList.scheduleLayoutAnimation()
            }
        }
    }

    private fun updateUserData(user: User) {
        binding.usernameText.text = user.name
        Glide.with(this)
            .load(user.avatar_url)
            .transition(withCrossFade())
            .into(binding.userImage)
    }

    override fun onRepositoryClick(repository: Repository) {
        val args = Bundle()
        args.putString(LAST_UPDATED_KEY, repository.updated_at)
        args.putInt(STARS_KEY, repository.stargazers_count!!)
        args.putInt(FORKS_KEY, repository.forks_count!!)
        val repoBottomSheetFragment = UserRepoBottomSheetFragment()
        repoBottomSheetFragment.arguments = args
        repoBottomSheetFragment.show(supportFragmentManager, UserRepoBottomSheetFragment.TAG)
    }

    companion object {
        const val LAST_UPDATED_KEY = "LastUpdated"
        const val STARS_KEY = "Stars"
        const val FORKS_KEY = "Forks"
    }
}