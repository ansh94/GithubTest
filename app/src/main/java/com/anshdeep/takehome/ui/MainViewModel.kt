package com.anshdeep.takehome.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshdeep.takehome.network.NetworkLayer
import com.anshdeep.takehome.network.response.Repository
import com.anshdeep.takehome.network.response.User
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    private val githubService = NetworkLayer.githubService

    private val _userLiveData = MutableLiveData<User?>()
    val userLiveData: LiveData<User?> = _userLiveData

    private val _userRepoData = MutableLiveData<List<Repository>?>()
    val userRepoData: LiveData<List<Repository>?> = _userRepoData

    fun fetchUserData(userId: String) {
        viewModelScope.launch {
            val userResponse = githubService.getUser(userId)
            if (userResponse.isSuccessful) {
                _userLiveData.postValue(userResponse.body())
            }
        }
    }

    fun fetchUserRepoData(userId: String) {
        viewModelScope.launch {
            val userRepoResponse = githubService.getUserRepositories(userId)
            if (userRepoResponse.isSuccessful) {
                _userRepoData.postValue(userRepoResponse.body())
            }
        }
    }
}