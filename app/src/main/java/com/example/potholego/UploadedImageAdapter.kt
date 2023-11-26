// UploadedImageAdapter.kt
package com.example.potholego

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UploadedImageAdapter(private val imageDatas: List<String>) :
    RecyclerView.Adapter<UploadedImageAdapter.UploadedImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadedImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_ex, parent, false)
        return UploadedImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: UploadedImageViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(imageDatas[position]).into(holder.uploadedImage)
    }

    override fun getItemCount(): Int {
        return imageDatas.size
    }

    inner class UploadedImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val uploadedImage: ImageView = itemView.findViewById(R.id.img_rv_photo)
    }
}