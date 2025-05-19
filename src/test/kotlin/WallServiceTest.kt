import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import ru.netology.Comment
import ru.netology.Like
import ru.netology.Post
import ru.netology.WallService
import ru.netology.exceptions.PostNotFoundException
import ru.netology.exceptions.ReportIdException
import ru.netology.exceptions.ReportReasonException
import ru.netology.wall_reports.ReportComment

class WallServiceTest {
    private val wall = WallService
    private val comment = Comment(10, 1, 19052025, "Test comment")
    private val likes = Like(200)
    private val reportComment = ReportComment(1, 100, 1)
    private val reportComment1 = ReportComment(1, 1, 11)


    @Before
    fun clearBeforeTest() {
        wall.clear()
    }

    @Test
    fun update_existingPost_shouldReturnTrue() {
        val post = Post(1, 2, 12345678, "Исходный текст", 0, 0, true, comment, likes)
        val added = wall.add(post)
        val updatedPost = Post(
            added.ownerId,
            added.fromId,
            added.date,
            "Обновленный текст",
            0,
            0,
            true,
            comment,
            likes,
            id = added.id
        )
        val result = wall.update(updatedPost)
        assertTrue(result)
        assertEquals("Обновленный текст", wall.posts[0].text)
    }

    @Test
    fun update_nonExistingPost_shouldReturnFalse() {
        val post = Post(1, 2, 12345678, "Текст", 0, 0, true, comment, likes, id = 999)
        val result = wall.update(post)
        assertFalse(result)
    }

    @Test
    fun update_shouldNotChangeArraySize() {
        wall.add(Post(1, 2, 12345678, "Тест", 0, 0, true, comment, likes))
        val nonExistentPost = Post(1, 2, 12345678, "Нет такого поста", 0, 0, true, comment, likes, id = 999)
        wall.update(nonExistentPost)
        assertEquals(1, wall.posts.size)
    }

    @Test
    fun add() {
        val post1 = Post(2, 3, 5052025, "Текст первого поста!", 4, 5, true, comment, likes)
        wall.add(post1)
        assertEquals(1, wall.posts.last().id)
    }

    @Test(expected = PostNotFoundException::class)
    fun createCommentException() {
        wall.createComment(1000, comment)
    }

    @Test
    fun createCommentSuccess() {
        val post1 = Post(2, 3, 5052025, "Текст первого поста!", 4, 5, true, comment, likes)
        wall.add(post1)
        assertEquals(comment, wall.createComment(1, comment))
    }

    @Test(expected = ReportIdException::class)
    fun reportCommentExceptionId(){
        val post = Post(1, 2, 12345678, "Исходный текст", 0, 0, true, comment, likes)
        wall.add(post)
        wall.createComment(1, comment)
        wall.reportComment(reportComment)
    }

    @Test(expected = ReportReasonException::class)
    fun reportCommentExceptionReason(){
        val post = Post(1, 2, 12345678, "Исходный текст", 0, 0, true, comment, likes)
        wall.add(post)
        wall.createComment(1, comment)
        wall.reportComment(reportComment1)
    }
}
