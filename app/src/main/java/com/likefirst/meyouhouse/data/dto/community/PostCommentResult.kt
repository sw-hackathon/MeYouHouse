package com.likefirst.meyouhouse.data.dto.community

import com.google.gson.annotations.SerializedName

data class PostCommentResult (
    @SerializedName("content") val content : String,
    @SerializedName("userId") val userId : Int,
    @SerializedName("postId") val postId : Int
)