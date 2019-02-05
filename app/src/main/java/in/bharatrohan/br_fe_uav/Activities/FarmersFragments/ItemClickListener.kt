package com.feedingtrends.vocally.Interfaces

import android.view.View

public interface ItemClickListener {
    /*fun onStopClick(view: View, position: Int, isLongClick: Boolean)*/

    fun onPhoneClick(view: View, position: Int)
    fun onSelectClick(view: View, position: Int)
}