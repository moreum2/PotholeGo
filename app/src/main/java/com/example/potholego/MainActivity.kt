package com.example.potholego

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var profileAdapter: ProfileAdapter
    val datas = mutableListOf<ProfileData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycler()
    }
    private fun initRecycler() {
        profileAdapter = ProfileAdapter(this, object: ProfileAdapter.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                //클릭 시 이벤트 처리
            }
        })
        val rv_profile = findViewById<RecyclerView>(R.id.rv_profile)
        rv_profile.adapter = profileAdapter

        datas.apply {
            add(ProfileData(img = R.drawable.pothole, name = "포트홀1", age = 1))
            add(ProfileData(img = R.drawable.pothole2, name = "포트홀2", age = 2))
            add(ProfileData(img = R.drawable.pothole3, name = "포트홀3", age = 3))
            add(ProfileData(img = R.drawable.pothole4, name = "포트홀4", age = 4))
            add(ProfileData(img = R.drawable.pothole5, name = "포트홀5", age = 5))

            profileAdapter.datas = datas
            profileAdapter.notifyDataSetChanged()

        }
    }
}