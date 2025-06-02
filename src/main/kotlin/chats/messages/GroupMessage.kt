package ru.netology.chats.messages

import ru.netology.attachments.Attachment

class GroupMessage(
    override var id: Int,
    override var message: String,
    override var isDeleted: Boolean = false,
    var attachments: MutableList<Attachment>? = null,
    var groupId: Int = 0
) : Message(id, message, isDeleted, attachments) {
}