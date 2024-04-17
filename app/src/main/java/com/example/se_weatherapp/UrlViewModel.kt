package com.example.se_weatherapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class UrlViewModel : ViewModel() {
        val urlLiveData = MutableLiveData<String>()

        fun fetchurl(url: String) {
                viewModelScope.launch {
                        // Dispatchers.IO (main-safety block)
                        withContext(Dispatchers.IO) {
                                fetchAsync(url)
                        }
                }
        }

        private fun fetchAsync(url: String) {
                urlLiveData.postValue(URL(url).readText())
        }
}
