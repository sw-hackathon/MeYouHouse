package com.likefirst.meyouhouse.ui.community

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.likefirst.meyouhouse.data.dto.community.DetailImage
import com.likefirst.meyouhouse.databinding.ItemDetailImageRvBinding

class DetailImageRVAdapter (private val Images : MutableList<DetailImage>) : RecyclerView.Adapter<DetailImageRVAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemDetailImageRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(Image: DetailImage) {
            Glide.with(binding.detailImageView.context)
                .load(Image.url)
                .into(binding.detailImageView)
            Log.d("Adapter",Image.url)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemDetailImageRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(Images[position])
    }

    override fun getItemCount(): Int {
        return Images.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list : MutableList<DetailImage>){
        Images.clear()
        Images.addAll(list)
        notifyDataSetChanged()
    }

}
