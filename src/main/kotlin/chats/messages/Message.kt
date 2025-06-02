package ru.netology.chats.messages

import ru.netology.attachments.Attachment

open class Message(
    open var id: Int,
    open var message: String,
    open var isDeleted: Boolean = false,
    attachments: MutableList<Attachment>? = null){
    var hasRead = false
}
