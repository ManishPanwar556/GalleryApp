package com.example.galleryapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.dialog_fragment.view.*

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
        view.buttonFull.setOnClickListener {
            val intent = Intent(activity, ImageActivity::class.java)
            startActivity(intent)
            dismiss()
        }
    }
}