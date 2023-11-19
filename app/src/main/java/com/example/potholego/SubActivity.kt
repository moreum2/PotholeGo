package com.example.potholego

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        // Retrieve data passed from MainActivity
        val itemName = intent.getStringExtra("selectedItemName")
        val itemAge = intent.getIntExtra("selectedItemAge", 0)

        // Do something with the data, e.g., display it in the SubActivity
        // ...
    }
}