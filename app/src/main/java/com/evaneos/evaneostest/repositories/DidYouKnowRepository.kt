package com.evaneos.evaneostest.repositories

import com.evaneos.data.FakeDestinationFetchingService
import com.evaneos.evaneostest.api.DestinationApi
import com.evaneos.evaneostest.api.DidYouKnowApi
import com.evaneos.evaneostest.dummy.FakeDidYouKnowService
import com.evaneos.evaneostest.model.DidYouKnow

class DidYouKnowRepository {

    @Volatile
    private var instance: DidYouKnowRepository? = null
    private val didYouKnowApi = DidYouKnowApi(FakeDidYouKnowService()).getInstance()


    fun getInstance(): DidYouKnowRepository {
        val result: DidYouKnowRepository? = instance
        if (result != null) {
            return result
        }
        synchronized(DestinationApi::class.java) {
            if (instance == null) {
                instance = DidYouKnowRepository()
            }
            return instance as DidYouKnowRepository
        }
    }

    suspend fun getListDidYouKnow(): List<DidYouKnow> {
        return didYouKnowApi.getListDidYouKnow()
    }
}