// MyApplication.kt
package com.example.potholego

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.naver.maps.map.NaverMapSdk

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        try {
            // Check if FirebaseApp is already initialized
            if (FirebaseApp.getApps(this).isEmpty()) {
                val options = FirebaseOptions.Builder()
                    .setApiKey("your_api_key")
                    .setApplicationId("your_app_id")
                    .setProjectId("your_project_id")
                    .build()
                FirebaseApp.initializeApp(this, options)
            }

            NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient("pq1dagiepp")
        } catch (e: Exception) {
            Log.e("MyApplication", "Firebase initialization error", e)
        }
    }
}
