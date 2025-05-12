package ru.netology.attachments

open class PhotoAttachment(override val type: String = "photo"): Attachment

data class Photo(
    val id: Int,
    val albumId: Int,
    val ownerId: Int,
    var text: String? = null,
    var hasTags: Boolean = false,
) : PhotoAttachment(){
    override fun toString(): String {
        val photo = "Изображение $id. Владелец: $ownerId. Альбом: $albumId"
        return photo
    }
}