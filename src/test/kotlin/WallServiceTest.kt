import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import ru.netology.Comment
import ru.netology.Like
import ru.netology.Post
import ru.netology.WallService

class WallServiceTest {
    private val wall1 = WallService
    private val comment1 = Comment(10)
    private val likes1 = Like(200)
    private val post1 = Post(2, 3, 5052025, "Текст первого поста!", 4, 5, true, comment1, likes1)
    private val post2 = Post(2, 3, 5052025, "Обновленный текст первого поста!", 4, 5, true, comment1, likes1)

    private val postForUpdate = Post(5, 6, 6052025, "Обновленный текст второго поста!", 4, 5, true, comment1, likes1, id = 1)


    @Before
    fun clearBeforeTest() {
        wall1.clear()
    }

    @Test
    fun add() {
        wall1.add(post1)
        assertEquals(1, wall1.posts.last().id)
    }

    @Test
    fun updateTrue() {
        wall1.add(post1)
        assertEquals(true, wall1.update(postForUpdate))
    }

    @Test
    fun updateFalse() {
        wall1.add(post1)
        assertEquals(false, wall1.update(post2))
    }
}