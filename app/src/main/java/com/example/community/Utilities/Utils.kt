package com.example.community.Utilities

import android.view.View
import android.widget.ImageView


fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}


