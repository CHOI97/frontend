package com.example.simya.server.story

data class OpenStoryDTO(
    var houseId: Long,
    var capacity: Int,
    var topic: com.example.simya.server.story.TopicRequestDTO
)