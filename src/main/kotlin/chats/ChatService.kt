package ru.netology.chats

import ru.netology.attachments.Attachment
import ru.netology.chats.messages.DirectMessage
import ru.netology.chats.messages.GroupMessage
import ru.netology.chats.messages.Message
import ru.netology.exceptions.MessageDeletedException

object ChatService {
    var allChats = mutableListOf<Chat>()
    private var newChatId = 0
    private var chatNumber = 1
    private var newMessageId = 0

    fun clear() {
        allChats.clear()
        newChatId = 0
        chatNumber = 1
        newMessageId = 0
    }

    private fun chatFilter(chatId: Int): Chat {
        return allChats.firstOrNull { it.id == chatId }
            ?: throw NoSuchElementException("Чат с ID: $chatId не найден")
    }

    val addNewChat = { isDirect: Boolean, senderId: Int, receiverId: Int, groupId: Int, firstMessage: String ->
        val newChat = Chat(
            id = newChatId,
            isDirect = isDirect,
            participantsId = listOf(senderId, receiverId),
            title = "New Chat №$chatNumber"
        )
        allChats.add(newChat)
        val newMessage = createMessage(firstMessage, null, newChat.id, senderId, groupId)
        newChat.messages.add(newMessage)
        newChatId++
        chatNumber++
    }


    val deleteChat = { chatId: Int ->
        val chatToDelete = chatFilter(chatId)
        if (chatToDelete.isDeleted) {
            throw MessageDeletedException("Чат с ID: $chatId уже удален")
        } else {
            chatToDelete.isDeleted = true
            chatToDelete.messages.forEach { it.isDeleted = true }
            allChats.remove(chatToDelete)
        }
    }

    fun getChats(): List<Chat> {
        return allChats
    }

    val createMessage =
        { text: String, attachments: MutableList<Attachment>?, chatId: Int, sender: Int, groupId: Int ->
            val chat = chatFilter(chatId)
            val newMessage: Message = when (chat.isDirect) {
                true -> DirectMessage(newMessageId, text, attachments = attachments).apply { senderId = sender }
                false -> GroupMessage(newMessageId, text, attachments = attachments, groupId = groupId).apply { }
            }
            chat.messages.add(newMessage)
            newMessage.id = newMessageId
            newMessageId++
            newMessage
        }

    val updateMessage =
        { chatId: Int, messageId: Int, newText: String ->
            val chat = chatFilter(chatId)
            val message = chat.messages.firstOrNull { it.id == messageId }
                ?: throw NoSuchElementException("Сообщение с ID: $messageId не найдено")
            if (message.isDeleted) {
                throw MessageDeletedException("Сообщение с ID: $messageId удалено")
            } else {
                message.message = newText
            }
        }


    val deleteMessage =
        { chatId: Int, messageId: Int ->
            val chat = chatFilter(chatId)
            val message = chat.messages.firstOrNull { it.id == messageId }
                ?: throw NoSuchElementException("Сообщение с ID: $messageId не найдено")
            if (message.isDeleted) {
                throw MessageDeletedException("Сообщение с ID: $messageId уже удалено")
            } else {
                message.isDeleted = true
                chat.messages.remove(message) // удаляем из списка
            }
        }

    fun getUnreadChatsCount(): List<Chat> {
        return allChats.filter { it.hasUnread }
    }

    fun getLastMessages(): List<Message> {
        val lastMessages = mutableListOf<Message>()
        allChats.forEach { chat ->
            val lastMsg = chat.messages.lastOrNull()
            if (lastMsg != null) {
                lastMessages.add(lastMsg)
            }
        }
        return lastMessages
    }

    fun getChatMessages(chatId: Int, count: Int): List<Message>? {
        val chat = chatFilter(chatId)
        val messages = chat.messages.filter { !it.isDeleted }
        if (messages.isEmpty()) {
            println("Нет сообщений")
            return null
        }
        val result = messages.takeLast(count).toMutableList()
        result.forEach { it.hasRead = true }
        return result
    }
}
