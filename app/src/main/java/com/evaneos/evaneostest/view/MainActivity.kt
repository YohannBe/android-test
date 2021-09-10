package com.evaneos.evaneostest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.evaneos.evaneostest.R
import com.evaneos.evaneostest.api.DestinationApi
import com.evaneos.evaneostest.viewmodels.DestinationsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var destinationsViewModel: DestinationsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        destinationsViewModel = DestinationsViewModel()

        destinationsViewModel.getDestinationList()

        destinationsViewModel.getResultDestinationList().observe(this,{
            for (destinationObject in it){
                Toast.makeText(this, destinationObject.name, Toast.LENGTH_SHORT).show()
            }
        })

        destinationsViewModel.getError().observe(this, {
            if (it)
                destinationsViewModel.getDestinationList()
        })
    }
}