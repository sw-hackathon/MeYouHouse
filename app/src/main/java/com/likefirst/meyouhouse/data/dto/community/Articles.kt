package com.likefirst.meyouhouse.data.dto.community

import com.google.gson.annotations.SerializedName

data class Articles(
    @SerializedName("items") val items : MutableList<Article>
)
