import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import ru.netology.Comment
import ru.netology.Like
import ru.netology.Post
import ru.netology.WallService

class WallServiceTest {
    private val wall = WallService
    private val comment = Comment(10)
    private val likes = Like(200)

    @Before
    fun clearBeforeTest() {
        wall.clear()
    }

    @Test
    fun update_existingPost_shouldReturnTrue() {
        val post = Post(1, 2, 12345678, "Исходный текст", 0, 0, true, comment, likes)
        val added = wall.add(post)
        val updatedPost = Post(added.ownerId, added.fromId, added.date, "Обновленный текст", 0, 0, true, comment, likes, id = added.id)
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
    fun add(){
        val post1 = Post(2, 3, 5052025, "Текст первого поста!", 4, 5, true, comment, likes)
        wall.add(post1)
        assertEquals(1, wall.posts.last().id)
    }
}
