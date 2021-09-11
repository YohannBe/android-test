package com.evaneos.evaneostest.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.evaneos.data.FakeDestinationFetchingService
import com.evaneos.data.model.DestinationDetails
import com.evaneos.evaneostest.api.DestinationApi
import com.evaneos.evaneostest.repositories.DestinationRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.lang.Exception

class DetailsViewModel : ViewModel() {

    private val destinationRepository: DestinationRepository = DestinationRepository(
        DestinationApi(
            FakeDestinationFetchingService()
        ).getInstance()
    ).getInstance()

    private val destinationDetails = MutableLiveData<DestinationDetails>()
    private val errorLoading = MutableLiveData<Boolean>()
    var job: Job? = null


    fun getDestinationDetails(id: Long) {
        job = CoroutineScope(IO).launch {
            try {
                val response = destinationRepository.getDestinationDetails(id)
                withContext(Main) {
                    destinationDetails.postValue(response)
                    errorLoading.value = false
                }
            } catch (e: Exception) {
                Log.e("error:", "there is an error with the details")
                withContext(Main) {
                    errorLoading.value = true
                }
            }

        }

    }

    fun getResultDestinationDetails(): LiveData<DestinationDetails> {
        return destinationDetails
    }

    fun getError(): LiveData<Boolean> {
        return errorLoading
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}