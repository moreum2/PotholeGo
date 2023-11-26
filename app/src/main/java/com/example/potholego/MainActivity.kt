// MainActivity.kt
package com.example.potholego

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.potholego.ProfileAdapter
import com.example.potholego.ProfileData
import com.example.potholego.R
import com.example.potholego.SubActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    val storage = FirebaseStorage.getInstance()
    var storageRef = storage.reference

    private lateinit var profileAdapter: ProfileAdapter
    private val datas = mutableListOf<ProfileData>()

    // 추가된 부분: FirebaseFirestore 인스턴스
    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pathReference = storageRef.child("images").listAll().addOnSuccessListener {
            it.items.forEach {
                Log.e("File name: ", it.name)
            }
        }
        setContentView(R.layout.activity_main)


        initRecycler()

        val switchButton: Switch = findViewById(R.id.switchButton)
        switchButton.setOnCheckedChangeListener { _, isChecked ->
            updateData(isChecked)
        }
    }

    private fun initRecycler() {
        profileAdapter = ProfileAdapter(this, object : ProfileAdapter.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                val selectedItem = profileAdapter.datas[position]

                // Create an Intent to launch the SubActivity
                val intent = Intent(this@MainActivity, SubActivity::class.java)

                // Pass data to the SubActivity (optional)
                intent.putExtra("data", selectedItem)

                // Start the SubActivity
                startActivity(intent)
            }
        })
        val rv_profile = findViewById<RecyclerView>(R.id.rv_profile)
        rv_profile.adapter = profileAdapter

        // 초기 데이터 설정
        updateData(false)
    }

    private fun updateData(showAll: Boolean) {
        datas.clear()

        // Coroutine을 사용하여 백그라운드 스레드에서 데이터 가져오기
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val querySnapshot = firestore.collection("images").get().await()
                for (document in querySnapshot.documents) {
                    val name = document.getString("name") ?: ""
                    val date = document.getString("date") ?: ""
                    val vibrationDetected = document.getBoolean("vibrationDetected") ?: false
                    val institution = document.getString("institution") ?: ""
                    val imageUrl = document.getString("imageUrl") ?: ""

                    // Firebase Storage에서 이미지 다운로드
                    val storageRef: StorageReference = storage.reference.child(imageUrl)
                    val uri = storageRef.downloadUrl.await()

                    withContext(Dispatchers.Main) {
                        datas.add(
                            ProfileData(
                                imgUrl = uri.toString(),
                                name = name,
                                date = date,
                                vibrationDetected = vibrationDetected,
                                institution = institution
                            )
                        )


                        // TextFileProcessor를 사용하여 텍스트 파일을 처리
                        val textFileProcessor = TextFileProcessor(storage)
                        textFileProcessor.processTextFile(
                            imageUrl,
                            onSuccess = { textFileUrl ->
                                // 텍스트 파일을 성공적으로 처리한 경우의 작업
                                Log.d("MainActivity", "Successfully processed text file: $textFileUrl")
                            },
                            onFailure = {
                                // 텍스트 파일 처리 실패 시의 작업
                                Log.e("MainActivity", "Failed to process text file")
                            }
                        )
                    }
                }
            } catch (exception: Exception){}
        }
    }
}
