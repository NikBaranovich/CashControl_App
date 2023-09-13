package com.example.example2.colorpicker

import android.content.Context

fun Int.dpToPx(context: Context) = (context.resources.displayMetrics.density * this).toFloat()