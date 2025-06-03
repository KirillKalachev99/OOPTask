import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.netology.chats.ChatService
import ru.netology.exceptions.MessageDeletedException

class ChatServiceTest {

    @Before
    fun setup() {
        ChatService.clear()
    }

    @Test
    fun addNewChat_ShouldCreateChatAndFirstMessage() {
        ChatService.addNewChat(true, 1, 2, 0, "Hello")
        val chat = ChatService.getChats().last()
        assertNotNull(chat)
        assertEquals("New Chat №1", chat.title)
        assertEquals(2, chat.participantsId.size)
        assertEquals(1, chat.participantsId[0])
        assertEquals(2, chat.participantsId[1])
    }

    @Test
    fun deleteChat_ShouldMarkAsDeletedAndAddToRemoved() {
        ChatService.addNewChat(true, 1, 2, 0, "Hi")
        val chatId = ChatService.getChats().last().id
        ChatService.deleteChat(chatId)
        val chat = ChatService.getDeletedChats().find { it.id == chatId }
        assertEquals(true, chat?.isDeleted)
    }

    @Test(expected = MessageDeletedException::class)
    fun deleteChat_ShouldThrowIfAlreadyDeleted() {
        ChatService.addNewChat(true, 1, 2, 0, "Hi")
        val chatId = ChatService.getChats().last().id
        ChatService.deleteChat(chatId)
        ChatService.deleteChat(chatId)
    }

    @Test
    fun createMessage_ShouldAddMessageToChat() {
        ChatService.addNewChat(true, 1, 2, 0, "Hi")
        val chat = ChatService.getChats().last()
        val message = ChatService.createMessage("New message", null, chat.id, 1, 0)
        assertEquals("New message", message.message)
    }

    @Test
    fun updateMessage_ShouldChangeMessageText() {
        ChatService.addNewChat(true, 1, 2, 0, "Hi")
        val chat = ChatService.getChats().last()
        val message = chat.messages.first()
        ChatService.updateMessage(chat.id, message.id, "Updated")
        assertEquals("Updated", message.message)
    }


    @Test(expected = NoSuchElementException::class)
    fun updateMessage_ShouldThrowIfMessageNotFound() {
        ChatService.addNewChat(true, 1, 2, 0, "Hi")
        val chat = ChatService.getChats().last()
        ChatService.updateMessage(chat.id, 999, "Updated")
    }

    @Test(expected = MessageDeletedException::class)
    fun updateMessage_ShouldThrowIfMessageDeleted() {
        ChatService.addNewChat(true, 1, 2, 0, "Hi")
        val chat = ChatService.getChats().last()
        val message = chat.messages.first()
        ChatService.deleteMessage(chat.id, message.id)
        ChatService.updateMessage(chat.id, message.id, "New message")
    }

    @Test
    fun deleteMessage_ShouldRemoveAndMarkDeleted() {
        ChatService.addNewChat(true, 1, 2, 0, "Hi")
        val chat = ChatService.getChats().last()
        val message = chat.messages.first()
        ChatService.deleteMessage(chat.id, message.id)
        assertTrue(message.isDeleted)
    }

    @Test(expected = MessageDeletedException::class)
    fun deleteMessage_ShouldThrowIfAlreadyDeleted() {
        ChatService.addNewChat(true, 1, 2, 0, "Hi")
        val chat = ChatService.getChats().last()
        val message = chat.messages.first()
        ChatService.deleteMessage(chat.id, message.id)
        ChatService.deleteMessage(chat.id, message.id)
    }

    @Test
    fun getUnreadChatsCount_ShouldReturnOnlyUnread() {
        ChatService.addNewChat(true, 1, 2, 0, "Msg1")
        ChatService.addNewChat(true, 3, 4, 0, "Msg2")
        val chats = ChatService.getChats()

        val chat1 = chats[chats.size - 2]
        val message1 = chat1.messages.first()
        message1.hasRead = false

        val chat2 = chats[chats.size - 1]
        val message2 = chat2.messages.first()
        message2.hasRead = false

        val unreadChats = ChatService.getUnreadChatsCount()
        assertEquals(2, unreadChats.size)
        assertTrue(unreadChats.all { it.messages.any { msg -> msg.hasRead == false } })
    }


    @Test
    fun getLastMessages_ShouldReturnLastMessages() {
        ChatService.addNewChat(true, 1, 2, 0, "Msg1")
        ChatService.addNewChat(true, 3, 4, 0, "Msg2")
        val lastMessages = ChatService.getLastMessages()
        assertEquals(2, lastMessages.size)
        assertTrue(lastMessages.any { it.message == "Msg1" })
        assertTrue(lastMessages.any { it.message == "Msg2" })
    }

    @Test
    fun getChatMessages_ShouldReturnLastNMessages() {
        ChatService.addNewChat(true, 1, 2, 0, "Msg1")
        val chat = ChatService.getChats().last()
        ChatService.createMessage("Msg2", null, chat.id, 1, 0)
        ChatService.createMessage("Msg3", null, chat.id, 1, 0)
        val messages = ChatService.getChatMessages(chat.id, 2)
        assertEquals(2, messages?.size)
        assertTrue(messages?.any { it.message == "Msg2" } ?: false)
        assertTrue(messages?.any { it.message == "Msg3" } ?: false)
    }

    @Test
    fun getChatMessages_ShouldReturnNullIfNoMessages() {
        ChatService.addNewChat(true, 1, 2, 0, "Msg1")
        val chat = ChatService.getChats().last()
        chat.messages.forEach { it.isDeleted = true }
        val result = ChatService.getChatMessages(chat.id, 1)
        assertNull(result)
    }
}
