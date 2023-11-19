package com.example.potholego

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import android.widget.Switch

class MainActivity : AppCompatActivity() {
    lateinit var profileAdapter: ProfileAdapter
    val datas = mutableListOf<ProfileData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycler()

        val switchButton: Switch = findViewById(R.id.switchButton)
        switchButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // 스위치가 켜진 상태
                // 추가로 원하는 작업을 수행하세요.
            } else {
                // 스위치가 꺼진 상태
                // 추가로 원하는 작업을 수행하세요.
            }
        }
    }

    private fun initRecycler() {
        profileAdapter = ProfileAdapter(this, object : ProfileAdapter.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                val selectedItem = datas[position]

                // Create an Intent to launch the SubActivity
                val intent = Intent(this@MainActivity, SubActivity::class.java)

                // Pass data to the SubActivity (optional)
                intent.putExtra("selectedItemName", selectedItem.name)
                intent.putExtra("selectedItemDate", selectedItem.date) // 변경된 부분

                // Start the SubActivity
                startActivity(intent)
            }
        })
        val rv_profile = findViewById<RecyclerView>(R.id.rv_profile)
        rv_profile.adapter = profileAdapter

        datas.apply {
            add(ProfileData(img = R.drawable.pothole, name = "수원장안구1", date = "2023-01-01", vibrationDetected = true)) // 변경된 부분
            add(ProfileData(img = R.drawable.pothole2, name = "수원장안구2", date = "2023-01-02", vibrationDetected = false)) // 변경된 부분
            add(ProfileData(img = R.drawable.pothole3, name = "수원장안구3", date = "2023-01-03", vibrationDetected = true)) // 변경된 부분
            add(ProfileData(img = R.drawable.pothole4, name = "수원장안구4", date = "2023-01-04", vibrationDetected = true)) // 변경된 부분
            add(ProfileData(img = R.drawable.pothole5, name = "수원장안구5", date = "2023-01-05", vibrationDetected = false)) // 변경된 부분
            add(ProfileData(img = R.drawable.pothole6, name = "수원장안구6", date = "2023-01-06", vibrationDetected = false)) // 변경된 부분
            add(ProfileData(img = R.drawable.pothole7, name = "수원장안구7", date = "2023-01-07", vibrationDetected = true)) // 변경된 부분
            add(ProfileData(img = R.drawable.pothole8, name = "수원장안구8", date = "2023-01-08", vibrationDetected = false)) // 변경된 부분
            add(ProfileData(img = R.drawable.pothole9, name = "수원장안구9", date = "2023-01-09", vibrationDetected = true)) // 변경된 부분
            add(ProfileData(img = R.drawable.pothole10, name = "수원장안구10", date = "2023-01-10", vibrationDetected = true)) // 변경된 부분

            profileAdapter.datas = datas
            profileAdapter.notifyDataSetChanged()
        }
    }
}
