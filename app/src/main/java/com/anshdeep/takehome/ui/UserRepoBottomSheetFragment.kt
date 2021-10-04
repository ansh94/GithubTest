package com.anshdeep.takehome.ui

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.anshdeep.takehome.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.*


class UserRepoBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "UserBottomSheetFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    private fun convertLastUpdatedTime(timeString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd, yyyy hh:mm:ss a", Locale.getDefault())
        val date = inputFormat.parse(timeString)
        return outputFormat.format(date)
    }


    override fun setupDialog(dialog: Dialog, style: Int) {
        val contentView = View.inflate(context, R.layout.fragment_repository_detail, null)
        dialog.setContentView(contentView)
        (contentView.parent as View).setBackgroundColor(Color.TRANSPARENT)
        val mArgs = arguments
        val lastUpdated = mArgs!!.getString(MainActivity.LAST_UPDATED_KEY)
        val stars = mArgs.getInt(MainActivity.STARS_KEY)
        val forks = mArgs.getInt(MainActivity.FORKS_KEY)
        dialog.findViewById<TextView>(R.id.lastUpdatedValue).text =
            convertLastUpdatedTime(lastUpdated!!)
        dialog.findViewById<TextView>(R.id.starsValue).text = stars.toString()
        dialog.findViewById<TextView>(R.id.forksValue).text = forks.toString()
    }
}