// SubActivity.kt
package com.example.potholego

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.fragment.app.FragmentTransaction

class SubActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        val data = intent.getParcelableExtra<ProfileData>("data")
        val imgProfile: ImageView = findViewById(R.id.img_profile)
        val tvInstitution: TextView = findViewById(R.id.tv_institution)
        val tvStatus: TextView = findViewById(R.id.tv_status)

        imgProfile.setImageResource(data?.img ?: 0)
        tvInstitution.text = data?.institution ?: ""
        val tvName: TextView = findViewById(R.id.tv_rv_name)
        tvName.text = data?.name ?: ""
        val tvDate: TextView = findViewById(R.id.tv_rv_age)
        tvDate.text = data?.date ?: ""

        if (data?.vibrationDetected == true) {
            tvStatus.visibility = View.VISIBLE
        } else {
            tvStatus.visibility = View.GONE
        }

        // ImageView 클릭 이벤트 처리
        imgProfile.setOnClickListener {
            // 전체화면 이미지를 표시하는 Fragment로 전환
            val fullScreenFragment = FullScreenImageDialogFragment().apply {
                arguments = Bundle().apply {
                    // 이미지 리소스 ID를 전달
                    putInt(FullScreenImageDialogFragment.ARG_IMAGE_RES_ID, data?.img ?: 0)
                }
            }

            // Fragment를 표시하기 위해 FragmentTransaction을 사용
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.dummyFullScreenView, fullScreenFragment)
                addToBackStack(null)
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                commit()
            }

            // dummyFullScreenView 화면에 보이게 설정
            findViewById<View>(R.id.dummyFullScreenView).setVisibility(View.VISIBLE)
        }
    }
}
