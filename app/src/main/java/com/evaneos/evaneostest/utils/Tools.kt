package com.evaneos.evaneostest.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.evaneos.data.model.Destination
import com.evaneos.evaneostest.R
import com.evaneos.evaneostest.databinding.ActivityDetailsBinding
import com.evaneos.evaneostest.databinding.ActivityMainBinding
import com.evaneos.evaneostest.databinding.DialogErrorLoadingBinding
import com.evaneos.evaneostest.viewmodels.DestinationsViewModel
import com.evaneos.evaneostest.viewmodels.DetailsViewModel
import java.util.*

fun initDialogError(
    context: Context,
    countTrial: Int,
    viewModel: Any,
    binding: Any,
    currentActivity: String,
    id: Long?
) {
    val dialogBuilder = AlertDialog.Builder(context)
    val bindingDialogError = DialogErrorLoadingBinding.inflate(LayoutInflater.from(context))
    val dialogView = bindingDialogError.root
    dialogBuilder.setView(dialogView)
    val alertDialog = dialogBuilder.create()

    if (countTrial >= 3) {
        bindingDialogError.textviewDescriptionErrorDialog.text =
            context.getString(R.string.over_error)
        bindingDialogError.buttonTryAgainDialogErrorOccurred.isEnabled = false
    }
    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.show()

    bindingDialogError.buttonTryAgainDialogErrorOccurred.setOnClickListener {
        when (currentActivity) {
            MAIN_ACTIVITY -> updateMainActivity(
                viewModel as DestinationsViewModel,
                binding as ActivityMainBinding
            )

            DETAILS_ACTIVITY -> updateDetailsActivity(
                viewModel as DetailsViewModel,
                binding as ActivityDetailsBinding,
                id!!
            )
        }
        alertDialog.dismiss()
    }
}

fun updateDetailsActivity(
    detailsViewModel: DetailsViewModel,
    activityDetailsBinding: ActivityDetailsBinding,
    id: Long
) {
    detailsViewModel.getDestinationDetails(id)
    activityDetailsBinding.progressbarLoadingContentDetailsActivity.visibility = View.VISIBLE
    activityDetailsBinding.textviewErrorDetailsActivity.visibility = View.GONE
}

fun updateMainActivity(
    destinationsViewModel: DestinationsViewModel,
    binding: ActivityMainBinding
) {
    destinationsViewModel.getDestinationList()
    binding.progressbarLoadingContentListDestination.visibility = View.VISIBLE
    binding.textviewErrorListDestination.visibility = View.GONE
    binding.includeDidYouKnow.didYouKnowLayout.visibility = View.VISIBLE
}
