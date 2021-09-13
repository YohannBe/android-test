package com.evaneos.evaneostest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.evaneos.data.FakeDestinationFetchingService
import com.evaneos.data.model.Destination
import com.evaneos.evaneostest.api.DestinationApi
import com.evaneos.evaneostest.viewmodels.DestinationsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.time.Instant

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ViewModelUnitTest {

    private lateinit var destinationApi: DestinationApi
    private val destinationsViewModel = DestinationsViewModel()
    private val destinationLiveData = MutableLiveData<List<Destination>>()
    private lateinit var observer: Observer<List<Destination>>

    private val list: List<Destination> = mutableListOf(
        Destination(
            id = 217,
            name = "Barbade",
            pictureUrl = "https://static1.evcdn.net/images/reduction/1027399_w-800_h-800_q-70_m-crop.jpg",
            tag = "Incontournable",
            rating = 5
        ),
        Destination(
            id = 50,
            name = "Arménie",
            pictureUrl = "https://static1.evcdn.net/images/reduction/1544481_w-800_h-800_q-70_m-crop.jpg",
            tag = "Incontournable",
            rating = 4
        ),
        Destination(
            id = 6,
            name = "Allemagne",
            pictureUrl = "https://static1.evcdn.net/images/reduction/1027397_w-800_h-800_q-70_m-crop.jpg",
            tag = "Incontournable",
            rating = 5
        ),
        Destination(
            id = 306,
            name = "Bali",
            pictureUrl = "https://static1.evcdn.net/images/reduction/1581674_w-800_h-800_q-70_m-crop.jpg",
            tag = "Incontournable",
            rating = 4
        )
    )

    @Before
    fun initBefore() = runBlocking{
        val fakeDestinationFetchingService = mock(FakeDestinationFetchingService::class.java)
        destinationApi = DestinationApi(fakeDestinationFetchingService)
        `when`(fakeDestinationFetchingService.getDestinations()).thenReturn(list)
        destinationLiveData.postValue(list)
        observer = mock(Observer::class.java) as Observer<List<Destination>>
    }



    @Test
    fun addition_isCorrect() {
        destinationsViewModel.getDestinationList()
        destinationsViewModel.getResultDestinationList().observeForever(observer)
        Mockito.verify(observer).onChanged(destinationLiveData.value)
        assertEquals(4, 2 + 2)
    }
}