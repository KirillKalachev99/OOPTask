package ru.netology.chats

import ru.netology.chats.messages.Message

data class Chat(
    val id: Int,
    var isEmpty: Boolean = true,
    var isDirect: Boolean = true,
    var isDeleted: Boolean = false,
    var messages: MutableList<Message> = mutableListOf(),
    var participantsId: List<Int> = listOf(),
    var title: String,
    var lastMessageDate: String? = null
)
