package ru.netology.chats

import ru.netology.attachments.Attachment
import ru.netology.chats.messages.DirectMessage
import ru.netology.chats.messages.GroupMessage
import ru.netology.chats.messages.Message
import ru.netology.exceptions.MessageDeletedException

object ChatService {
    var allChats = mutableListOf<Chat>()
    var removedChats = mutableListOf<Chat>()
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
        val chat = allChats.firstOrNull { it.id == chatId }
        if (chat == null) {
            val emptyChat = addNewChat(true, 0, 0, 0, "Empty chat")
            return emptyChat
        } else return chat
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
        newChat
    }

    fun deleteChat(chatId: Int) {
        val chatToDelete = allChats.find { it.id == chatId }
            ?: throw NoSuchElementException("Чат с ID: $chatId не найден")
        if (chatToDelete.isDeleted) {
            throw MessageDeletedException("Чат с ID: $chatId уже удален")
        }
        chatToDelete.isDeleted = true
        chatToDelete.messages.forEach { it.isDeleted = true }
        removedChats.add(chatToDelete)
    }


    fun getChats(): List<Chat> {
        return allChats
    }

    fun getDeletedChats(): List<Chat> {
        return removedChats
    }

    val createMessage =
        { text: String, attachments: MutableList<Attachment>?, chatId: Int, sender: Int, groupId: Int ->
            val chat = chatFilter(chatId)
            if (chat.title == "Empty chat") {
                chat.apply {
                    isEmpty = false
                    if (groupId != 0) isDirect = false
                    title = "New Chat №$chatNumber"
                }
            }
            val newMessage: Message = when (chat.isDirect) {
                true -> DirectMessage(newMessageId, text, attachments = attachments).apply { senderId = sender }
                false -> GroupMessage(newMessageId, text, attachments = attachments, groupId = groupId).apply { }
            }
            chat.messages.add(newMessage)
            newMessage.id = newMessageId
            chatNumber++
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
                chat.messages.remove(message)
            }
        }

    fun getUnreadChatsCount(): List<Chat> {
        return allChats.filter { chat ->
            chat.messages.any { !it.isDeleted && it.hasRead == false }
        }
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
