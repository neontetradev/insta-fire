package com.neontetra.filmshare

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neontetra.filmshare.models.Posts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.neontetra.filmshare.databinding.ActivityLoginBinding.bind
import com.neontetra.filmshare.databinding.ActivityLoginBinding.inflate
import com.neontetra.filmshare.databinding.SinglePostBinding.inflate
import kotlinx.android.synthetic.main.single_post.*
import kotlinx.android.synthetic.main.single_post.view.*

class Adapter(val context: Context, val postList: MutableList<Posts>): RecyclerView.Adapter<Adapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_post, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount() = postList.size


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(post: Posts){
            itemView.username.text = post.user?.username
            itemView.description.text = post.description
            itemView.creation_time.text = DateUtils.getRelativeTimeSpanString(post.creationTime)
            Glide.with(context).load(post.imageUrl).into(itemView.image)

        }

    }

}