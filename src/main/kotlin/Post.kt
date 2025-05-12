package ru.netology

data class Post(
    val ownerId: Int,
    val fromId: Int,
    val date: Int,
    val text: String,
    val replyOwnerId: Int,
    val replyPostId: Int,
    val friendsOnly: Boolean = true,
    val comments: Comment,
    val likes: Like,
    var id: Int = 0
)