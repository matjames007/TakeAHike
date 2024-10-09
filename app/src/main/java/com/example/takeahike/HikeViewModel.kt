package com.example.takeahike

import android.provider.UserDictionary.Words
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.takeahike.model.Hike
import com.example.takeahike.model.HikeRepository
import kotlinx.coroutines.launch

class HikeViewModel(private val repository: HikeRepository): ViewModel() {
    //val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()
    val allHikes: LiveData<List<Hike>> = repository.allHikes.asLiveData()

    fun insert(hike: Hike) = viewModelScope.launch {
        repository.insert(hike)
    }
}

class HikeViewModelFactory(private val repository: HikeRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HikeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HikeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}