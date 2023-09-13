package com.likefirst.meyouhouse.ui.community

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.likefirst.meyouhouse.data.dto.community.Article
import com.likefirst.meyouhouse.databinding.ItemCommunityRvBinding
import java.text.SimpleDateFormat
import java.util.*

class ArticleRVAdapter(val Articles : MutableList<Article>, private val viewOnClickListener : (Int,String)->Unit) : RecyclerView.Adapter<ArticleRVAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemCommunityRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article : Article) {
            binding.articleBodyTextView.text = article.content
            binding.communityDate.text = article.createdAt
            binding.commentCountTextView.text = article.commentCnt.toString()
            binding.root.setOnClickListener {
                viewOnClickListener(article.postId,article.createdAt)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCommunityRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(Articles[position])
    }

    override fun getItemCount(): Int {
        return Articles.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setData(list : MutableList<Article>){
        Articles.clear()
        Articles.addAll(list)
        notifyDataSetChanged()
    }

}
