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

    // 추가된 부분: FirebaseFirestore 인스턴스
    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
                val textlist_onebyte: Long = 1024 * 1024
                val imagelist = storageRef.child("images").listAll().await().items
                val textlist = storageRef.child("texts").listAll().await().items.map {
                    it.getBytes(textlist_onebyte).await().toString(Charsets.UTF_8).split("\t")
                }
                val profileDataList = imagelist.zip(textlist) { l1, l2 ->
                    ProfileData(imgUrl = l1.downloadUrl.await().toString(), name = l2[0], date = l2[1], vibrationDetected = l2[2].toBoolean(), institution = l2[3], latitude = l2[4].toFloat(), longitude = l2[5].toFloat() )
                }
                profileAdapter.datas = profileDataList.toMutableList()
        }

        setContentView(R.layout.activity_main)


        initRecycler()

        val switchButton: Switch = findViewById(R.id.switchButton)
        switchButton.setOnCheckedChangeListener { _, isChecked ->
            profileAdapter.showVibratedOnly = isChecked
            profileAdapter.updateData()
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
