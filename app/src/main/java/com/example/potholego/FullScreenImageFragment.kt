// FullScreenImageFragment.kt
package com.example.potholego

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import android.graphics.BitmapFactory
import android.content.Intent
import android.widget.Toast
import android.net.Uri




class FullScreenImageDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "FullScreenImageDialogFragment"
        const val ARG_IMAGE_RES_ID = "imageResId"
    }

    private lateinit var btnSavePhoto: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_full_screen_image, container, false)

        // 이미지 리소스 ID를 인자로 받습니다.
        val imageResId = arguments?.getInt(ARG_IMAGE_RES_ID) ?: 0

        btnSavePhoto = view.findViewById(R.id.btnSavePhoto)
        btnSavePhoto.setOnClickListener {
            saveImageToGallery(requireContext(), imageResId)
        }


        return view
    }

    fun setImageResource(resource: String, resourceId: Int){
        view?.findViewById<ImageView>(R.id.imageViewFullScreen)?.let { imageView ->
            Glide.with(this)
                .load(resourceId)
                .into(imageView)
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }

    private fun saveImageToGallery(context: Context, imageResId: Int) {
        val bitmap = BitmapFactory.decodeResource(context.resources, imageResId)

        val filename = "${System.currentTimeMillis()}.jpg"
        var fos: FileOutputStream? = null

        try {
            val imageFile = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                filename
            )

            fos = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)

            // 갤러리에 스캔을 요청하여 사진을 갱신합니다.
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val contentUri = Uri.fromFile(imageFile)
            mediaScanIntent.data = contentUri
            context.sendBroadcast(mediaScanIntent)

            Toast.makeText(context, "사진이 갤러리에 저장되었습니다.", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Toast.makeText(context, "사진 저장에 실패하였습니다.", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        } finally {
            fos?.close()
        }
    }
}