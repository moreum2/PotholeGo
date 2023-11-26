package com.example.potholego

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage


class LoadActivity : AppCompatActivity() {
    var load: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_recycler_ex)
        load = findViewById<View>(R.id.img_rv_photo) as ImageView
        val storage = FirebaseStorage.getInstance()
        val storageReference = storage.reference
        val pathReference = storageReference.child("pothole1.jpg")
        pathReference.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(this@LoadActivity).load(uri).into(load!!)
        }.addOnFailureListener { }
    }
}