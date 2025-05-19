package ru.netology

import ru.netology.exceptions.PostNotFoundException
import java.util.*

object WallService {
    private var posts = emptyArray<Post>()
    private var commets = emptyArray<Comment>()
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

    fun createComment(postId: Int, comment: Comment): Comment {
        val commentId = UUID.randomUUID().toString()
        val postExist = posts.any { it.id == postId }
        if (!postExist) throw PostNotFoundException("Пост с ID $postId не существует!")
        val newComment = comment.copy(id = commentId)
        commets += newComment
        return comment
    }

    override fun toString(): String {
        return posts.contentDeepToString()
    }
}

