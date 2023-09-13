package com.likefirst.meyouhouse.data.dto.community

import com.google.gson.annotations.SerializedName

data class DetailArticle(
    @SerializedName("postId") val postId : Int,
    @SerializedName("content") val content : String,
    @SerializedName("imgs") val imgs : MutableList<String>,
    @SerializedName("comments") val comments : MutableList<Comment>
)
