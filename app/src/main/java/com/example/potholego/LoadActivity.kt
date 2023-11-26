//LoadActivity.kt
package com.example.potholego

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage

class LoadActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    val storage = FirebaseStorage.getInstance()
    var storageRef = storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load) // 적절한 레이아웃 파일로 변경

        imageView = findViewById(R.id.load_photo)

        // Create a reference with an initial file path and name
        val pathReference = storageRef.child("images/pothole1.jpg")

        // 다운로드 URL을 비동기적으로 가져옴
        pathReference.downloadUrl.addOnSuccessListener { uri ->
            // Glide를 사용하여 이미지를 ImageView에 로드
            Glide.with(this)
                .load(uri)
                .into(imageView)
        }.addOnFailureListener { exception ->
            // 다운로드 실패 시 처리
            // 예를 들어 로그 출력 등을 여기에 추가할 수 있습니다.
        }
    }
}
