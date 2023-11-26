// FirebaseStorageHelper.kt
package com.example.potholego

import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await

class FirebaseStorageHelper {

    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val storageRef: StorageReference = storage.reference

    // suspend 함수로 변경
    suspend fun processImageAndTextFiles(callback: (List<Pair<String, String>>) -> Unit) {
        val imageFolderRef: StorageReference = storageRef.child("gs://pothole-1412.appspot.com/")

        try {
            val listResult = imageFolderRef.listAll().await()

            val filePairs = mutableListOf<Pair<String, String>>()

            // 반복문으로 이미지와 텍스트 파일을 찾아서 리스트에 추가
            for (item in listResult.items) {
                val fileName = item.name
                if (fileName.endsWith(".jpg")) {
                    val imageFileName = fileName
                    val textFileName = fileName.replace(".jpg", ".txt")

                    // 해당 이미지 파일과 텍스트 파일을 리스트에 추가
                    filePairs.add(Pair(imageFileName, textFileName))
                }
            }

            callback.invoke(filePairs)

            // 로그에 출력
            Log.d("FirebaseStorage", "Image and text file pairs: $filePairs")
        } catch (exception: Exception) {
            Log.e("FirebaseStorage", "Error getting file names", exception)
            callback.invoke(emptyList())  // 예외 발생 시 콜백을 호출하여 빈 리스트를 전달
        }
    }
}
