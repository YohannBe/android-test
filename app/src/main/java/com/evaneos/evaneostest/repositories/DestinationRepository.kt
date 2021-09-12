package com.evaneos.evaneostest.repositories

import com.evaneos.data.FakeDestinationFetchingService
import com.evaneos.data.model.Destination
import com.evaneos.data.model.DestinationDetails
import com.evaneos.evaneostest.api.DestinationApi

class DestinationRepository {

    @Volatile
    private var instance: DestinationRepository? = null
    private val destinationApi = DestinationApi(FakeDestinationFetchingService()).getInstance()


    fun getInstance(): DestinationRepository {
        val result: DestinationRepository? = instance
        if (result != null) {
            return result
        }
        synchronized(DestinationApi::class.java) {
            if (instance == null) {
                instance = DestinationRepository()
            }
            return instance as DestinationRepository
        }
    }

    suspend fun getDestinationList(): List<Destination> {
        return destinationApi.getDestinationList()
    }

    suspend fun getDestinationDetails(id: Long): DestinationDetails {
        return destinationApi.getDestinationDetails(id)
    }

}