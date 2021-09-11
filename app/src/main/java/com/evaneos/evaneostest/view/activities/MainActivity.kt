package com.evaneos.evaneostest.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.evaneos.evaneostest.R
import com.evaneos.evaneostest.databinding.ActivityMainBinding
import com.evaneos.evaneostest.utils.MAIN_ACTIVITY
import com.evaneos.evaneostest.utils.initDialogError
import com.evaneos.evaneostest.view.recyclerview.ListDestinationRecyclerViewAdapter
import com.evaneos.evaneostest.viewmodels.DestinationsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var destinationsViewModel: DestinationsViewModel
    private lateinit var binding: ActivityMainBinding
    private val adapter = ListDestinationRecyclerViewAdapter(this)
    private var countTrial: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initRecyclerView()
        initViewModel()
    }

    private fun initRecyclerView() {
        binding.recyclerViewListDestination.adapter = adapter
        binding.recyclerViewListDestination.layoutManager = LinearLayoutManager(this)
    }

    private fun initViewModel() {
        destinationsViewModel = DestinationsViewModel()
        destinationsViewModel.getDestinationList()

        destinationsViewModel.getResultDestinationList().observe(this, {
            adapter.updateDestinationList(it)
            binding.progressbarLoadingContentListDestination.visibility = View.GONE
            binding.textviewErrorListDestination.visibility = View.GONE
        })

        destinationsViewModel.getListEmpty().observe(this, {
            if (it) {
                binding.progressbarLoadingContentListDestination.visibility = View.GONE
                binding.textviewErrorListDestination.text = getString(R.string.empty_list)
                binding.textviewErrorListDestination.visibility = View.VISIBLE
            }
        })

        destinationsViewModel.getError().observe(this, {
            if (it) {
                countTrial++
                binding.progressbarLoadingContentListDestination.visibility = View.GONE

                initDialogError(
                    this,
                    countTrial,
                    destinationsViewModel,
                    binding,
                    MAIN_ACTIVITY,
                    null
                )
                binding.textviewErrorListDestination.text = getString(R.string.error_sorry)
                binding.textviewErrorListDestination.visibility = View.VISIBLE
            }
        })
    }
}