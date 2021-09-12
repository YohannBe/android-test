package com.evaneos.evaneostest.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.evaneos.data.model.Destination
import com.evaneos.evaneostest.model.DidYouKnow
import com.evaneos.evaneostest.repositories.DestinationRepository
import com.evaneos.evaneostest.repositories.DidYouKnowRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.lang.Exception
import kotlin.random.Random

class DestinationsViewModel : ViewModel() {

    private val destinationRepository: DestinationRepository = DestinationRepository().getInstance()
    private val didYouKnowRepository: DidYouKnowRepository = DidYouKnowRepository().getInstance()

    private val destinationList = MutableLiveData<List<Destination>>()
    private val errorLoading = MutableLiveData<Boolean>()
    private val emptyListError = MutableLiveData<Boolean>()
    private val listFact = MutableLiveData<List<DidYouKnow>>()
    private var job: Job? = null


    fun getDestinationList() {
        job = CoroutineScope(IO).launch {
            try {
                val response = destinationRepository.getDestinationList()
                withContext(Main) {
                    if (response.isNotEmpty()) {
                        val sortedList = response.sortedBy { it.name }
                        destinationList.postValue(sortedList)
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

    fun getListFact(){
        job = CoroutineScope(IO).launch {
            val response = didYouKnowRepository.getListDidYouKnow()
            withContext(Main){
                listFact.postValue(response)
            }
        }
    }

    fun getUniqueFact(list: List<DidYouKnow>): DidYouKnow {
        val index = Random.nextInt(0, list.size-1)
        return list[index]
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

    fun getListFactData(): LiveData<List<DidYouKnow>> {
        return listFact
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}