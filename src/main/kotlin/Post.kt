package ru.netology

data class Post(
    val ownerId: Int,
    val fromId: Int,
    val date: Int,
    val text: String,
    val replyOwnerId: Int? = null,
    val replyPostId: Int? = null,
    val friendsOnly: Boolean = true,
    val comments: Comment,
    val likes: Like,
    var id: Int = 0
)