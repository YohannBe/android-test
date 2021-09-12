package com.evaneos.evaneostest.dummy

import com.evaneos.evaneostest.model.DidYouKnow

class FakeDidYouKnowService {

    suspend fun getListDidYouKnow(): List<DidYouKnow>{
        return DidYouKnowFactory.createDidYouKnowList()
    }
}