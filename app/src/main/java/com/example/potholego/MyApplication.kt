package com.example.potholego

import android.app.Application
import com.naver.maps.map.NaverMapSdk

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient("pq1dagiepp");
    }
}