package com.likefirst.meyouhouse.data.dto.community

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("postId") val postId : Int,
    @SerializedName("content") val content : String,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("commentCnt") val commentCnt : Int
)