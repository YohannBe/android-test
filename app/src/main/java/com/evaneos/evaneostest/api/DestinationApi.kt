package com.evaneos.evaneostest.api

import com.evaneos.data.FakeDestinationFetchingService
import com.evaneos.data.model.Destination
import com.evaneos.data.model.DestinationDetails


class DestinationApi {

    @Volatile
    private  var instance: DestinationApi? = null

    private fun DestinationApi() {}

    fun getInstance(): DestinationApi {
        val result: DestinationApi? = instance
        if (result != null) {
            return result
        }
        synchronized(DestinationApi::class.java) {
            if (instance == null) {
                instance  = com.evaneos.evaneostest.api.DestinationApi()
            }
            return instance as DestinationApi
        }
    }

    private val fakeDestinationFetchingService = FakeDestinationFetchingService()

    suspend fun getDestinationList(): List<Destination> {
        return fakeDestinationFetchingService.getDestinations()
    }

    suspend fun getDestinationDetails(id: Long): DestinationDetails {
        return fakeDestinationFetchingService.getDestinationDetails(id)
    }
}