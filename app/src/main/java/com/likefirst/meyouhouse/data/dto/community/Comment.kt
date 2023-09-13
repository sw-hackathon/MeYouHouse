package com.likefirst.meyouhouse.data.dto.community

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("id") val id : Int,
    @SerializedName("content") val content : String,
    @SerializedName("createdAt") val createdAt : String
)
