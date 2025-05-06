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
        for (index in posts.indices) {
            if (posts[index].id == post.id) {
                posts[index] = post.copy()
                return true
            }
        }
        println("Вы пытаетесь обновить несуществующий пост!")
        return false
    }


    fun clear() {
        posts = emptyArray()
        postId = 1
    }

    override fun toString(): String {
        return posts.contentDeepToString()
    }
}

