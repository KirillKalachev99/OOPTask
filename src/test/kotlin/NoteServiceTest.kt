import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import ru.netology.NoteService
import ru.netology.exceptions.*

class NoteServiceTest {
    private val noteWall = NoteService
    private val privacyForTest = noteWall.privacy[0]

    @Before
    fun clearBeforeTest() {
        noteWall.clear()
    }

    @Test
    fun add() {
        noteWall.add("Тестовый заголовок", "Тестовый текст", privacyForTest, privacyForTest)
        assertEquals(1, noteWall.notes.last().noteId)
    }

    @Test
    fun createComment() {
        noteWall.add("Тестовый заголовок", "Тестовый текст", privacyForTest, privacyForTest)
        noteWall.createComment(1, "Тест", 1)
        assertEquals("Тест", noteWall.comments.last().text)
    }

    @Test
    fun deleteOk() {
        noteWall.add("Тестовый заголовок", "Тестовый текст", privacyForTest, privacyForTest)
        val checher = noteWall.delete(1)
        assertEquals(1, checher)
    }

    @Test(expected = NoteNotFoundException::class)
    fun deleteExc1() {
        noteWall.delete(1)
    }

    @Test(expected = NoteDeletedException::class)
    fun deleteExc2() {
        noteWall.add("Тестовый заголовок", "Тестовый текст", privacyForTest, privacyForTest)
        noteWall.delete(1)
        noteWall.delete(1)
    }

    @Test
    fun deleteCommentOk() {
        noteWall.add("Тестовый заголовок", "Тестовый текст", privacyForTest, privacyForTest)
        noteWall.createComment(1, "Тест", 1)
        noteWall.deleteComment(1)
        assertEquals(true, noteWall.comments.last().isDeleted)
    }

    @Test(expected = CommentNotFoundException::class)
    fun deleteCommentExc1() {
        noteWall.deleteComment(1)
    }

    @Test(expected = CommentDeletedException::class)
    fun deleteCommentExc2() {
        noteWall.add("Тестовый заголовок", "Тестовый текст", privacyForTest, privacyForTest)
        noteWall.createComment(1, "Тест", 1)
        noteWall.deleteComment(1)
        noteWall.deleteComment(1)
    }

    @Test
    fun edit() {
        noteWall.add("Тестовый заголовок", "Тестовый текст", privacyForTest, privacyForTest)
        val edited =
            noteWall.edit(1, "НОВЫЙ Тестовый заголовок", "НОВЫЙ Тестовый текст", privacyForTest, privacyForTest)
        assertEquals(1, edited)
    }

    @Test
    fun editComment() {
        noteWall.add("Тестовый заголовок", "Тестовый текст", privacyForTest, privacyForTest)
        noteWall.createComment(1, "Тест", 1)
        noteWall.editComment(1, "Тестовые ляляля")
        val com = noteWall.comments.last().text
        assertEquals("Тестовые ляляля", com)
    }

    @Test
    fun get() {
        noteWall.add("Тестовый заголовок", "Тестовый текст", privacyForTest, privacyForTest)
        noteWall.add("Тестовый заголовок1", "Тестовый текст1", privacyForTest, privacyForTest)
        noteWall.add("Тестовый заголовок2", "Тестовый текст2", privacyForTest, privacyForTest)

        val notes = noteWall.get(intArrayOf(1, 2, 3), 3)

        assertEquals("Тестовый текст2", notes.last().text)
    }

    @Test
    fun getById() {
        noteWall.add("Тестовый заголовок", "Тестовый текст", privacyForTest, privacyForTest)
        noteWall.add("Тестовый заголовок1", "Тестовый текст1", privacyForTest, privacyForTest)

        val notes = noteWall.getById(2)

        assertEquals(2, notes.last().noteId)
    }

    @Test
    fun getComments() {
        noteWall.add("Тестовый заголовок", "Тестовый текст", privacyForTest, privacyForTest)
        noteWall.createComment(1, "Тест", 1)
        noteWall.createComment(1, "Тест1", 1)
        noteWall.createComment(1, "Тест2", 1)

        val comments = noteWall.getComments(1, 3)

        assertEquals(3, comments.size)
    }

    @Test
    fun restoreComment() {
        noteWall.add("Тестовый заголовок", "Тестовый текст", privacyForTest, privacyForTest)
        noteWall.createComment(1, "Тест", 1)
        noteWall.deleteComment(1)
        val restored = noteWall.restoreComment(1)
        assertEquals(1, restored)
    }
}