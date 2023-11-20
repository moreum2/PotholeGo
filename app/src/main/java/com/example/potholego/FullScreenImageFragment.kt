// FullScreenImageFragment.kt
package com.example.potholego

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide

class FullScreenImageDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "FullScreenImageDialogFragment"
        const val ARG_IMAGE_RES_ID = "imageResId"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_full_screen_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 이미지 리소스 ID를 인자로 받습니다.
        val imageResId = arguments?.getInt(ARG_IMAGE_RES_ID) ?: 0

        // Glide를 사용하여 이미지를 ImageView에 로드합니다.
        view.findViewById<ImageView>(R.id.imageViewFullScreen)?.let { imageView ->
            Glide.with(this)
                .load(imageResId)
                .into(imageView)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }
}
