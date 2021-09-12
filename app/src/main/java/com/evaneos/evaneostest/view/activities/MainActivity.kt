package com.evaneos.evaneostest.view.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.evaneos.evaneostest.R
import com.evaneos.evaneostest.databinding.ActivityMainBinding
import com.evaneos.evaneostest.model.DidYouKnow
import com.evaneos.evaneostest.utils.MAIN_ACTIVITY
import com.evaneos.evaneostest.utils.initDialogError
import com.evaneos.evaneostest.view.recyclerview.ListDestinationRecyclerViewAdapter
import com.evaneos.evaneostest.viewmodels.DestinationsViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var destinationsViewModel: DestinationsViewModel
    private lateinit var binding: ActivityMainBinding
    private val adapter = ListDestinationRecyclerViewAdapter(this)
    private var countTrial: Int = 0
    private lateinit var listFactsRetrieved: List<DidYouKnow>

    private val appear: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.appear_pop_up
        )
    }

    private val disappear: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.disappear_pop_up
        )
    }

    private val rotate: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.change_fact
        )
    }

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
        destinationsViewModel.getListFact()

        listFactDataObserve()
        destinationListObserve()
        listEmptyObserve()
        listErrorObserve()
    }

    private fun listErrorObserve() {
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
                binding.includeDidYouKnow.didYouKnowLayout.visibility = View.GONE
            }
        })
    }

    private fun listEmptyObserve() {
        destinationsViewModel.getListEmpty().observe(this, {
            if (it) {
                binding.progressbarLoadingContentListDestination.visibility = View.GONE
                binding.textviewErrorListDestination.text = getString(R.string.empty_list)
                binding.textviewErrorListDestination.visibility = View.VISIBLE
            }
        })
    }

    private fun destinationListObserve() {
        destinationsViewModel.getResultDestinationList().observe(this, {
            adapter.updateDestinationList(it)
            binding.progressbarLoadingContentListDestination.visibility = View.GONE
            binding.textviewErrorListDestination.visibility = View.GONE
        })
    }

    private fun listFactDataObserve() {
        destinationsViewModel.getListFactData().observe(this, {
            listFactsRetrieved = it
            binding.includeDidYouKnow.didYouKnowLayout.startAnimation(appear)
            binding.includeDidYouKnow.didYouKnowLayout.visibility = View.VISIBLE
            updatePopUp(destinationsViewModel.getUniqueFact(listFactsRetrieved))
        })
    }

    private fun updatePopUp(fact: DidYouKnow) {
        binding.includeDidYouKnow.textviewDescriptionDidYouKnow.text = fact.description
        Glide.with(this)
            .load(fact.imageUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.includeDidYouKnow.imageviewDidYouKnow)

        clickListenerFact(fact)
    }

    private fun clickListenerFact(fact: DidYouKnow) {
        binding.includeDidYouKnow.textviewDescriptionDidYouKnow.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(fact.link))
            startActivity(browserIntent)
        }

        binding.includeDidYouKnow.buttonCloseDidyouknow.setOnClickListener{
            binding.includeDidYouKnow.didYouKnowLayout.startAnimation(disappear)
            binding.includeDidYouKnow.didYouKnowLayout.visibility = View.GONE
        }

        binding.includeDidYouKnow.buttonAnotherDidyouknow.setOnClickListener {
            updatePopUp(destinationsViewModel.getUniqueFact(listFactsRetrieved))
        }
    }
}