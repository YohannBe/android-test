package com.evaneos.evaneostest.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.evaneos.data.FakeDestinationFetchingService
import com.evaneos.data.model.Destination
import com.evaneos.evaneostest.api.DestinationApi
import com.evaneos.evaneostest.repositories.DestinationRepository
import com.evaneos.evaneostest.utils.NameAZComparator
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.lang.Exception

class DestinationsViewModel : ViewModel() {

    private val destinationRepository: DestinationRepository = DestinationRepository(
        DestinationApi(
        FakeDestinationFetchingService()
    ).getInstance()).getInstance()

    private val destinationList = MutableLiveData<List<Destination>>()
    private val errorLoading = MutableLiveData<Boolean>()
    private val emptyListError = MutableLiveData<Boolean>()
    var job: Job? = null


    fun getDestinationList() {
        job = CoroutineScope(IO).launch {
            try {
                val response = destinationRepository.getDestinationList()
                withContext(Main) {
                    if (response.isNotEmpty()) {
                        response.sortedWith(NameAZComparator())
                        destinationList.postValue(response)
                        errorLoading.value = false
                        emptyListError.value = false
                    } else {
                        emptyListError.value = true
                        Log.e("error:", "list is empty")
                    }
                }
            } catch (e: Exception) {
                Log.e("error:", "there is an error")
                withContext(Main) {
                    errorLoading.value = true
                }
            }
        }
    }

    fun getResultDestinationList(): LiveData<List<Destination>> {
        return destinationList
    }

    fun getError(): LiveData<Boolean> {
        return errorLoading
    }

    fun getListEmpty(): LiveData<Boolean> {
        return emptyListError
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}