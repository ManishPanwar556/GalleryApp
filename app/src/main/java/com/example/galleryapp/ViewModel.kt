package com.example.galleryapp

import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat

class ViewModel(application: Application):AndroidViewModel(application){
    private var _images=MutableLiveData<List<Image>>()
    val images:LiveData<List<Image>>
    get() = _images

    fun getImage(){
        _images.value=queryImage()
    }
    private fun queryImage():List<Image>{
        val imageList= mutableListOf<Image>()
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_TAKEN
        )
        val selection = "${MediaStore.Images.Media.DATE_TAKEN}>=?"

        val selectionArgs = arrayOf(datetoTimeStamp("01", "01", "2020"))

        val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"

        getApplication<Application>().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use {cursor->
            val idColumn=cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val dateColumn=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)
            while(cursor.moveToNext()){
                val id=cursor.getLong(idColumn)
                val name=cursor.getString(nameColumn)
                val date=cursor.getString(dateColumn)

                val contentUri: Uri =ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,id)
                imageList+=Image(contentUri,name,date)
            }
        }
        return imageList
    }
    private fun datetoTimeStamp(day: String, month: String, year: String): String {
        var wholeDate = "$day-${month}-$year"
        var formatter = SimpleDateFormat("dd-MM-yyyy")
        var date = formatter.parse(wholeDate)
        return date.time.toString()
    }
}