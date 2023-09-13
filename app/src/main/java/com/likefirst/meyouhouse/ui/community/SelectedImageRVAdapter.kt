package com.likefirst.meyouhouse.ui.community

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.likefirst.meyouhouse.data.dto.community.Article
import com.likefirst.meyouhouse.data.dto.community.SelectedImage
import com.likefirst.meyouhouse.databinding.ItemCommunityRvBinding
import com.likefirst.meyouhouse.databinding.ItemSelectedImageRvBinding

class SelectedImageRVAdapter(val SelectedImages : MutableList<SelectedImage>) : RecyclerView.Adapter<SelectedImageRVAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemSelectedImageRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(SelectedImage : SelectedImage) {
            binding.selectedImageView.setImageURI(SelectedImage.uri)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSelectedImageRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(SelectedImages[position])
    }

    override fun getItemCount(): Int {
        return SelectedImages.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list : MutableList<SelectedImage>){
        SelectedImages.clear()
        SelectedImages.addAll(list)
        notifyDataSetChanged()
    }

}