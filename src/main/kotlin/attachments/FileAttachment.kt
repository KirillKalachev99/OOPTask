package ru.netology.attachments

open class FileAttachment(override val type: String = "file"): Attachment

data class File(
    val id: Int,
    val ownerId: Int,
    var title: String,
    var size: Int,
    var ext: String,
    var url: String
) : FileAttachment(){
    override fun toString(): String {
        val file = "Файл $id: $title.$ext. Владелец: $ownerId. Размер файла: $size кб. Ссылка на файл: $url"
        return file
    }
}