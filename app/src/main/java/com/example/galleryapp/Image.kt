package com.example.galleryapp

import android.net.Uri

data class Image(
    val uri: Uri,
    val name: String,
    val date: String
)