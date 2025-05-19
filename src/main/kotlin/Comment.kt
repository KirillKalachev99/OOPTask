package ru.netology

data class Comment(
    var id: Int,
    val fromId: Int,
    val date: Int,
    val text: String,
    val replyToUser: Int? = null,
    val replyToComment: Int? = null
)



