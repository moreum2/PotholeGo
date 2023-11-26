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
        lifecycleScope.launch {
            val imagelist = storageRef.child("images").listAll().await()
            imagelist.items.map {
                ProfileData(imageUrl = it.downloadUrl.await())
            }
        }

        lifecycleScope.launch {
            val textlist = storageRef.child("texts").listAll().await()

            val textlist_onebyte: Long = 1024 * 1024
            textlist.items.map {
                val bytes = it.getBytes(textlist_onebyte).await()

                // 바이트 배열을 UTF-8 문자열로 변환
                val utf8String = bytes.toString(Charsets.UTF_8).split("\t")

                // 결과 확인을 위해 로그에 출력
                Log.e("test", utf8String.toString())

                ProfileData(name = utf8String[0], date = utf8String[1], vibrationDetected = utf8String[2].toBoolean(), institution = utf8String[3] )
            }
        }

        setContentView(R.layout.activity_main)


        initRecycler()

        val switchButton: Switch = findViewById(R.id.switchButton)
        switchButton.setOnCheckedChangeListener { _, isChecked ->
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
    }


}
