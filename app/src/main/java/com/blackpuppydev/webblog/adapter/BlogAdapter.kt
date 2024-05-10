package com.blackpuppydev.webblog.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blackpuppydev.webblog.R
import com.blackpuppydev.webblog.api.Api
import com.blackpuppydev.webblog.api.ApiRepository
import com.blackpuppydev.webblog.constance.LandingPage
import kotlinx.android.synthetic.main.blog_adapter.view.*
import kotlin.collections.ArrayList

abstract class BlogAdapter(private var blogList: ArrayList<ApiRepository.Data>?) : RecyclerView.Adapter<BlogAdapter.BlogViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        return BlogViewHolder(parent)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {


        holder.setData(blogList!![position])

        holder.itemView.btn_bookmark.setOnClickListener{
            if (blogList!![position].bookMark){
                holder.itemView.btn_bookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
                blogList!![position].bookMark = false
            } else {
                holder.itemView.btn_bookmark.setImageResource(R.drawable.ic_baseline_bookmark_24)
                blogList!![position].bookMark = true
            }

            onItemClick(blogList!!)
        }

        holder.itemView.btn_share.setOnClickListener{}

        holder.itemView.setOnClickListener{
            if (!blogList!![position].read){
                holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
                blogList!![position].read = true
                onItemClick(blogList!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return blogList!!.size
    }

    abstract fun onItemClick(currentBlog:ArrayList<ApiRepository.Data>)

    inner class BlogViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.blog_adapter, parent, false)){

        @SuppressLint("ResourceAsColor")
        fun setData(currentBlog: ApiRepository.Data){
            itemView.title.text = currentBlog.title
            itemView.title_detail.text = currentBlog.body

            if (currentBlog.bookMark){
                itemView.btn_bookmark.setImageResource(R.drawable.ic_baseline_bookmark_24)
            } else itemView.btn_bookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24)

            if (currentBlog.read){
                itemView.setBackgroundColor(Color.parseColor("#ffffff"))
            }else{
                itemView.setBackgroundColor(Color.parseColor("#c2dfeb"))
            }

        }
    }
}