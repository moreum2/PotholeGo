// TextFileProcessor.kt
package com.example.potholego

import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class TextFileProcessor(private val storage: FirebaseStorage) {

    fun processTextFile(fileName: String, onSuccess: (String) -> Unit, onFailure: () -> Unit) {
        val storageRef: StorageReference = storage.reference.child("$fileName.txt")

        storageRef.downloadUrl.addOnSuccessListener { uri ->
            // 이미지에 대한 텍스트 파일의 다운로드 URL을 얻음

            // 이 URL을 사용하여 파일을 읽어오는 작업을 수행
            // 여기서는 파일을 읽어오는 방법을 제공하지만, 실제로는 본인의 파일 읽기 로직을 적용해야 함
            val textFileUrl = uri.toString()
            Log.d("TextFileURL", textFileUrl)

            // 텍스트 파일을 성공적으로 읽었을 때의 처리
            onSuccess(textFileUrl)
        }.addOnFailureListener {
            // 텍스트 파일 다운로드 실패 시 처리
            onFailure()
        }
    }
}
