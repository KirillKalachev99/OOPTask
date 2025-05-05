package ru.netology

object WallService {
    var posts = emptyArray<Post>()

    fun add(post: Post): Post {
        posts += post
        return posts.last()
    }

    fun update(post: Post): Boolean {
        for ((i, p) in posts.withIndex()) {
            if (post.id == p.id) {
                posts[i] = post
                return true
            } else {
                println("Вы пытаетесь обновить несуществующий пост!")
                return false
            }
        }
        return false
    }

    override fun toString(): String {
        return posts.contentDeepToString()
    }
}

