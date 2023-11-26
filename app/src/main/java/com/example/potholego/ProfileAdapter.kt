// ProfileAdapter.kt
package com.example.potholego

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProfileAdapter(val context: Context, val listener: OnItemClickListener) :
    RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(v: View, position: Int)
    }

    private var allDatas = mutableListOf<ProfileData>() // 전체 목록을 유지하는 변수
    private var filteredDatas = mutableListOf<ProfileData>() // 필터링된 목록을 유지하는 변수
    private var showVibratedOnly = false // 진동 감지 여부에 따라 필터링할지 여부를 결정하는 변수

    var datas = mutableListOf<ProfileData>()
        set(value) {
            field = value
            allDatas.clear()
            allDatas.addAll(value) // 데이터 갱신 시 전체 목록도 갱신
            updateData() // 데이터 갱신 시 필터링된 목록도 갱신
        }

    init {
        filteredDatas.addAll(datas) // 초기에 필터링된 목록도 전체 목록으로 시작
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler_ex, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = filteredDatas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredDatas[position])
    }

    fun updateData() {
        // 진동 감지 여부에 따라 데이터를 필터링
        filteredDatas = if (showVibratedOnly) {
            allDatas.filter { it.vibrationDetected }.toMutableList()
        } else {
            allDatas.toMutableList()
        }

        notifyDataSetChanged() // 어댑터에 데이터 변경을 알림
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtName: TextView = itemView.findViewById(R.id.tv_rv_name)
        private val txtDate: TextView = itemView.findViewById(R.id.tv_rv_age)
        private val txtStatus: TextView = itemView.findViewById(R.id.tv_status) // 추가된 부분
        private val imgProfile: ImageView = itemView.findViewById(R.id.img_rv_photo)

        fun bind(item: ProfileData) {
            txtName.text = item.name
            txtDate.text = item.date.toString()
            Glide.with(itemView).load(item.imgUrl).into(imgProfile)

            // 진동 감지 여부에 따라 텍스트뷰를 처리
            if (item.vibrationDetected) {
                txtStatus.visibility = View.VISIBLE
            } else {
                txtStatus.visibility = View.GONE
            }

            itemView.setOnClickListener {
                Intent(context, SubActivity::class.java).apply {
                    putExtra("data", item)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }
        }
    }
}
