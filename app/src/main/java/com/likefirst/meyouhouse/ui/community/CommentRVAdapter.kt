package com.likefirst.meyouhouse.ui.community

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.likefirst.meyouhouse.data.dto.community.Article
import com.likefirst.meyouhouse.data.dto.community.Comment
import com.likefirst.meyouhouse.databinding.ItemCommentRvBinding
import com.likefirst.meyouhouse.databinding.ItemCommunityRvBinding

class CommentRVAdapter(val Comments : MutableList<Comment>) : RecyclerView.Adapter<CommentRVAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemCommentRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment : Comment) {
            binding.commentBodyTextView.text = comment.content
            binding.commentDetailDate.text = comment.createdAt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCommentRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(Comments[position])
    }

    override fun getItemCount(): Int {
        return Comments.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list : MutableList<Comment>){
        Comments.clear()
        Comments.addAll(list)
        notifyDataSetChanged()
    }

}
