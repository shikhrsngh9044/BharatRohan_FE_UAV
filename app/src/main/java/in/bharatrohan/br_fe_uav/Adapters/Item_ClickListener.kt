package com.feedingtrends.vocally.Interfaces

import android.view.View

public interface Item_ClickListener {
    /*fun onStopClick(view: View, position: Int, isLongClick: Boolean)*/

    fun onOptionClick(view: View, position: Int)
    fun onSelectClick(view: View, position: Int)
}