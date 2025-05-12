package ru.netology.attachments

open class VideoAttachment(override val type: String = "video"): Attachment

data class Video(
    val id: Int,
    val ownerId: Int,
    var title: String,
    val description: String? = null,
    val duration: Int,
    ) : VideoAttachment(){
    override fun toString(): String {
        val video = "Видеозапись $id: $title. Владелец: $ownerId. Длительность: $duration"
        return video
    }
    }