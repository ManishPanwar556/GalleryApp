package com.example.galleryapp

import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.jar.Manifest

class MainActivity : AppCompatActivity(), ItemClickListener {
    lateinit var list: List<MediaStore.Images>

    companion object {
        val PERMISSIONS = arrayOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        val REQUEST_CODE_PERMISSION = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val model = ViewModel(application)
        setContentView(R.layout.activity_main)
        if (permissionGranted()) {
           loadImage(model)

        } else {
//            requestPermission
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE_PERMISSION)
        }

    }
   fun loadImage(model: ViewModel){
       model.getImage()
       model.images.observe(this, Observer {
           rev.adapter = MyAdapter(it, this)
           rev.layoutManager = GridLayoutManager(this, 5)
       })
   }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (permissionGranted()) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun permissionGranted() =
        checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    override fun onclick(uri: Uri, position: Int) {
        Dialog(uri).show(supportFragmentManager, "fragment")
    }
}