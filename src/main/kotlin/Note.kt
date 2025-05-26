package ru.netology

import ru.netology.attachments.Attachment

data class Note(
    var noteId: Int,
    var title: String,
    var text: String,
    var privacy: Int = 3,
    var commentPrivacy: Int = 3,
    var isDeleted: Boolean = false,
    var attach: Array<Attachment>? = null,
    var comments: Array<Comment>? = null
)
