package com.example.galleryapp

import android.net.Uri

interface ItemClickListener {
    fun onclick(uri: Uri,position:Int)
}