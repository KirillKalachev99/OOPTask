package ru.netology

object WallService {
    var posts = emptyArray<Post>()
    private var postId = 1

    fun add(post: Post): Post {
        post.id = postId
        posts += post.copy(id = postId)
        postId++
        return posts.last()
    }

    fun update(post: Post): Boolean {
        for (p in posts) {
            if (post.id == p.id) {
                posts[p.id - 1] = post.copy()
                return true
            }
        }
        println("Вы пытаетесь обновить несуществующий пост!")
        return false
    }

    fun clear() {
        posts = emptyArray()
    }

    override fun toString(): String {
        return posts.contentDeepToString()
    }
}

