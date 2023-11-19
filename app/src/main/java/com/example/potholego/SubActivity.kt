package com.example.potholego

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.view.View

class SubActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        // Intent로 전달된 데이터를 받음
        val data = intent.getParcelableExtra<ProfileData>("data")

        // ImageView와 TextView를 참조
        val imgProfile: ImageView = findViewById(R.id.img_profile)
        val tvInstitution: TextView = findViewById(R.id.tv_institution)
        val tvStatus: TextView = findViewById(R.id.tv_status)

        // 받아온 데이터를 화면에 표시
        imgProfile.setImageResource(data?.img ?: 0)
        tvInstitution.text = data?.institution ?: ""

        // Display other data as needed
        val tvName: TextView = findViewById(R.id.tv_rv_name)
        tvName.text = data?.name ?: ""

        val tvDate: TextView = findViewById(R.id.tv_rv_age)
        tvDate.text = data?.date ?: ""

        // Check if vibration is detected and show/hide the status text accordingly
        if (data?.vibrationDetected == true) {
            tvStatus.visibility = View.VISIBLE
        } else {
            tvStatus.visibility = View.GONE
        }

        // Add more TextViews for other data fields if necessary
    }
}