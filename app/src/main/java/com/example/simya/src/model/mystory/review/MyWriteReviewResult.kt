package com.example.simya.src.model.mystory.review

import com.example.simya.src.model.HouseDTO
import com.example.simya.src.model.UserDTO
import com.google.gson.annotations.SerializedName

data class MyWriteReviewResult(
    @SerializedName("reviewedHouse") val reviewHouse: HouseDTO,
    @SerializedName("myReview") val myReview: MyReview,
){
    data class MyReview(
        @SerializedName("reviewersProfile") val reviewersProfile: UserDTO,
        @SerializedName("reviewId") val reviewId: Long,
        @SerializedName("rate") val rate: Long,
        @SerializedName("content") val content: Long,

        )
}
