package ru.netology

import ru.netology.attachments.Attachment

data class Post(
    val ownerId: Int,
    val fromId: Int,
    val date: Int,
    val text: String,
    val replyOwnerId: Int? = null,
    val replyPostId: Int? = null,
    val friendsOnly: Boolean = true,
    var comments: Comment? = null,
    val likes: Like,
    var id: Int = 0,
    var attach: Array<Attachment>? = null
)