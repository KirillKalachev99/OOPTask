package ru.netology.attachments

open class AudioAttachment(override val type: String = "audio") : Attachment

data class Audio(
    val id: Int,
    val ownerId: Int,
    val artist: String,
    var title: String,
    val duration: Int,
    val lyricsId: Int? = null
) : AudioAttachment(){
    override fun toString(): String {
        val audio = "Аудиозапись $id: $title. Исполнитель: $artist. Длительность: $duration"
        return audio
    }
}