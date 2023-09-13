package com.likefirst.meyouhouse.data.dto.community

import com.google.gson.annotations.SerializedName

data class DetailImage(
    @SerializedName("imgs") val url : MutableList<String>,
)
