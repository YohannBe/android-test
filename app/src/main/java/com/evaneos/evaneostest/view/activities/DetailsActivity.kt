package com.evaneos.evaneostest.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import com.evaneos.evaneostest.databinding.ActivityDetailsBinding
import com.evaneos.evaneostest.utils.DETAILS_ACTIVITY
import com.evaneos.evaneostest.utils.ID_DESTINATION
import com.evaneos.evaneostest.utils.initDialogError
import com.evaneos.evaneostest.viewmodels.DetailsViewModel

class DetailsActivity : AppCompatActivity() {

    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var binding: ActivityDetailsBinding
    private var countTrial: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        initViewModel(intent.getLongExtra(ID_DESTINATION, -1))
    }

    private fun initViewModel(id: Long) {
        detailsViewModel = DetailsViewModel()
        if (id.toInt() != -1) {
            detailsViewModel.getDestinationDetails(id)

            detailsViewModel.getResultDestinationDetails().observe(this, {
                binding.includeToolbar.toolbarId.title = it.name
                binding.webviewDetailsActivity.webViewClient = WebViewClient()
                binding.webviewDetailsActivity.apply {
                    loadUrl(it.url)
                }
            })

            detailsViewModel.getError().observe(this, {
                if (it) {
                    countTrial++
                    initDialogError(this, countTrial, detailsViewModel, binding, DETAILS_ACTIVITY, id)
                    binding.progressbarLoadingContentDetailsActivity.visibility = View.GONE
                    binding.textviewErrorDetailsActivity.visibility = View.VISIBLE
                }
            })
        } else binding.textviewErrorDetailsActivity.visibility = View.VISIBLE
    }
}