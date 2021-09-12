package com.evaneos.evaneostest.api

import com.evaneos.evaneostest.dummy.FakeDidYouKnowService
import com.evaneos.evaneostest.model.DidYouKnow

class DidYouKnowApi(private val didYouKnowService: FakeDidYouKnowService) {

    @Volatile
    private  var instance: DidYouKnowApi? = null

    fun getInstance(): DidYouKnowApi {
        val result: DidYouKnowApi? = instance
        if (result != null) {
            return result
        }
        synchronized(DestinationApi::class.java) {
            if (instance == null) {
                instance  = DidYouKnowApi(FakeDidYouKnowService())
            }
            return instance as DidYouKnowApi
        }
    }

    suspend fun getListDidYouKnow(): List<DidYouKnow> {
        return didYouKnowService.getListDidYouKnow()
    }

}