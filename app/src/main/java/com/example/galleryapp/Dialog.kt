package com.example.galleryapp

import android.app.AlertDialog
import android.app.Application
import android.app.Dialog
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.dialog_fragment.view.*
import java.text.SimpleDateFormat

class Dialog(val uri: Uri) : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Glide.with(view).load(uri).into(view.profileImage)
        view.buttonDelete.setOnClickListener {
            deletePhoto(uri)
            dismiss()
        }
        view.buttonShare.setOnClickListener {
            var intent=Intent(Intent.ACTION_SEND)
            intent.setType("image/jpg")
            intent.putExtra(Intent.EXTRA_STREAM,uri)
            startActivity(Intent.createChooser(intent,"Share Image Using"))
            dismiss()
        }
    }
    private fun deletePhoto(uri:Uri){
        val selection = "${MediaStore.Images.Media.DATE_TAKEN}>=?"

        val selectionArgs = arrayOf(datetoTimeStamp("01", "01", "2020"))
       context!!.contentResolver.delete(uri,selection,selectionArgs)
        Toast.makeText(activity,"Successfully deleted ${uri.path}",Toast.LENGTH_SHORT).show()
    }
    private fun datetoTimeStamp(day: String, month: String, year: String): String {
        var wholeDate = "$day-${month}-$year"
        var formatter = SimpleDateFormat("dd-MM-yyyy")
        var date = formatter.parse(wholeDate)
        return date.time.toString()
    }
}