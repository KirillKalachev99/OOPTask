package ru.netology

import ru.netology.wall_reports.ReportComment
import ru.netology.exceptions.PostNotFoundException
import ru.netology.exceptions.ReportIdException
import ru.netology.exceptions.ReportReasonException

object WallService {
    var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var reportsComment = emptyArray<ReportComment>()
    private var commentId = 1
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
        comments = emptyArray()
        reportsComment = emptyArray()
        postId = 1
        commentId = 1
    }

    fun createComment(postId: Int, comment: Comment): Comment {
        val postExist = posts.any { it.id == postId }
        if (!postExist) throw PostNotFoundException("Пост с ID $postId не существует!")
        val newComment = comment.copy(id = commentId)
        comments += newComment
        commentId++
        return newComment
    }

    fun reportComment(report: ReportComment): ReportComment {
        val commentId = report.commentId
        val commentExist = comments.any { it.id == report.commentId }
        val reason = report.reason
        val reasonExist = reason in report.reasonsList.indices

        if (!commentExist) throw ReportIdException("Комментария с указанным ID $commentId не существует")
        else if (!reasonExist) throw ReportReasonException("Выбрана некорректная причина жалобы")
        else {
            reportsComment += report
            return report
        }
    }

    override fun toString(): String {
        return posts.contentDeepToString()
    }
}

