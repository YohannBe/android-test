package com.evaneos.evaneostest.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.evaneos.data.model.Destination
import com.evaneos.evaneostest.repositories.DestinationRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class DestinationsViewModel : ViewModel() {

    private val destinationRepository: DestinationRepository = DestinationRepository().getInstance()
    private val destinationList = MutableLiveData<List<Destination>>()
    private val loading = MutableLiveData<Boolean>()
    private val errorLoading = MutableLiveData<Boolean>()
    var job: Job? = null


    fun getDestinationList(){
         job = CoroutineScope(Dispatchers.IO).launch {
            val response = destinationRepository.getDestinationList()
            withContext(Main) {
                if (response.isNotEmpty()) {
                    destinationList.postValue(response)
                    loading.value = false
                    errorLoading.value = false
                }
                else {
                    errorLoading.value = true
                    Log.e("error:", "there is an error")
                }
            }
        }

    }

    fun getResultDestinationList():  LiveData<List<Destination>> {
        return destinationList
    }

    fun getError():  LiveData<Boolean> {
        return errorLoading
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}