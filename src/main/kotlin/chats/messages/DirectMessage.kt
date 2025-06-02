package ru.netology.chats.messages

import ru.netology.attachments.Attachment

class DirectMessage(
    override var id: Int,
    override var message: String,
    override var isDeleted: Boolean= false,
    var attachments: MutableList<Attachment>? = null
) : Message(id, message, isDeleted, attachments) {
    var receiverId = 0
    var senderId = 0
}