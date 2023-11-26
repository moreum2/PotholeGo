// SubActivity.kt
package com.example.potholego

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage

class SubActivity : AppCompatActivity() {

    val storage = FirebaseStorage.getInstance()
    var storageRef = storage.reference

    lateinit var mFragment: FullScreenImageDialogFragment

    override fun onBackPressed() {
        if (!mFragment.isHidden) {
            var transaction = supportFragmentManager.beginTransaction()
            transaction.hide(mFragment)
            transaction.commit()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        val data = intent.getParcelableExtra<ProfileData>("data")
        val imgProfile: ImageView = findViewById(R.id.img_profile)
        val tvInstitution: TextView = findViewById(R.id.tv_institution)
        val tvStatus: TextView = findViewById(R.id.tv_status)

        Glide.with(this).load(data?.imgUrl).into(imgProfile)


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
            mFragment.setImageResource(FullScreenImageDialogFragment.ARG_IMAGE_RES_ID, data?.imgUrl ?: "")

            // Fragment를 표시하기 위해 FragmentTransaction을 사용
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.dummyFullScreenView, mFragment)

            // dummyFullScreenView를 숨기고, fragment_full_screen_image를 보이게 설정
            transaction.show(mFragment)

            // 트랜잭션을 커밋
            transaction.commit()
        }
        mFragment = supportFragmentManager.findFragmentById(R.id.dummyFullScreenView) as FullScreenImageDialogFragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.hide(mFragment)
        transaction.commit()
    }
}