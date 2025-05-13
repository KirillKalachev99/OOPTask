package ru.netology.attachments

open class StoryAttachment(override val type: String = "story"): Attachment

data class Story(
    val id: Int,
    val ownerId: Int,
    var date: Int,
    var expiresAt: Int,
    var isExpired: Boolean = false,
    var isDeleted: Boolean = false
) : StoryAttachment()