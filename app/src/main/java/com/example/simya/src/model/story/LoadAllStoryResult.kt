package com.example.simya.src.model.story

data class LoadAllStoryResult(
    var houseId: Long,
    var category: String,
    var signboardImageUrl: String,
    var houseName: String,
    var todayTopicTitle: String
)