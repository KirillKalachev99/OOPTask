package ru.netology

import ru.netology.exceptions.*

const val CODE_OK = 1

object NoteService {
    val privacy = intArrayOf(0, 1, 2, 3)
    var notes = emptyArray<Note>()
    var comments = emptyArray<Comment>()
    private var newNoteId = 1
    private var newCommentId = 1


    fun add(title: String, text: String, privacy: Int, commentPrivacy: Int): Int {
        val newNote = Note(newNoteId, title, text, privacy, commentPrivacy)
        notes += newNote.copy()
        newNoteId++
        return newNote.noteId
    }

    fun createComment(noteId: Int, message: String, ownerId: Int): Int {
        val noteIndex = checkNote(noteId)
        val currentDate = DateMethod().getCurrentDate()
        val newComment = Comment(newCommentId, ownerId, currentDate, message)
        comments += newComment.copy()
        newCommentId++
        val note = notes[noteIndex]
        val updatedComments = note.comments?.plus(newComment) ?: arrayOf(newComment)
        notes[noteIndex] = note.copy(comments = updatedComments)
        return newCommentId
    }


    private fun checkNote(noteId: Int, word: String = "удалить, уже"): Int {
        val noteIndex = notes.indexOfFirst { it.noteId == noteId }
        if (noteIndex == -1) {
            throw NoteNotFoundException("Заметки с указанным ID: $noteId не существует")
        }
        val note = notes[noteIndex]
        if (note.isDeleted) {
            throw NoteDeletedException("Заметка с ID: $noteId, которую Вы хотите $word удалена")
        }
        return noteIndex
    }

    private fun checkComment(commentId: Int, word: String = "удалить, уже"): Int {
        val commentIndex = comments.indexOfFirst { it.id == commentId }
        if (commentIndex == -1) {
            throw CommentNotFoundException("Комментария с указанным ID: $commentId не существует")
        }
        val comment = notes[commentIndex]
        if (comment.isDeleted) {
            throw CommentDeletedException("Комментарий с ID: $commentId, который Вы хотите $word удален")
        }
        return commentIndex
    }

    fun delete(noteId: Int): Int {
        val noteChecked = checkNote(noteId)
        val note = notes[noteChecked]
        notes[noteChecked] = note.copy(isDeleted = true)
        return CODE_OK
    }

    fun deleteComment(commentId: Int): Int {
        val commentIndex = checkComment(commentId, "удалить,")
        val comment = comments[commentIndex]
        if (comment.isDeleted) {
            throw CommentDeletedException("Комментарий с ID: $commentId, который Вы хотите удалить, уже удален")
        }
        comments[commentIndex] = comment.copy(isDeleted = true)
        return CODE_OK
    }

    fun edit(noteId: Int, title: String, text: String, privacy: Int, commentPrivacy: Int): Int {
        val noteChecked = checkNote(noteId, "обновить,")
        val note = notes[noteChecked]
        notes[noteChecked] = note.copy(
            noteId = noteId,
            title = title,
            text = text,
            privacy = privacy,
            commentPrivacy = commentPrivacy
        )
        return CODE_OK
    }

    fun editComment(commentId: Int, message: String): Int {
        if (message.length < 2) throw CommentLengthException("Минимальная длина комментария - 2 символа")
        val commentChecked = checkComment(commentId, "изменить,")
        val comment = comments[commentChecked]
        comments[commentChecked] = comment.copy(text = message)
        return CODE_OK
    }

    fun get(noteIds: IntArray, count: Int): List<Note> {
        val resultNotes = mutableListOf<Note>()
        var countIter = 0

        for (noteId in noteIds) {
            if (countIter >= count) break
            val noteIndex = checkNote(noteId, "найти,")
            val note = notes[noteIndex]
            resultNotes.add(note)
            countIter++
        }
        return resultNotes
    }

    fun getById(noteId: Int): List<Note> {
        val resultNotes = mutableListOf<Note>()
        val noteChecked = checkNote(noteId, "найти,")
        resultNotes.add(notes[noteChecked])
        return resultNotes
    }

    fun getComments(noteId: Int, count: Int): Array<Comment> {
        val noteIndex = checkNote(noteId, "найти,")
        val note = notes[noteIndex]
        val commentsList = note.comments ?: emptyArray()
        val limit = minOf(count, commentsList.size)
        return commentsList.copyOfRange(0, limit)
    }



    fun restoreComment(commentId: Int): Int {
        val commentIndex = comments.indexOfFirst { it.id == commentId }
        if (commentIndex == -1) {
            throw NoteNotFoundException("Комментария с указанным ID: $commentId не существует")
        } else if (!comments[commentIndex].isDeleted) {
            throw CommentRestoredException("Комментарий с указанным ID: $commentId не удален")
        }
        comments[commentIndex].isDeleted = false
        return CODE_OK
    }

    fun clear() {
        notes = emptyArray()
        comments = emptyArray()
        newNoteId = 1
        newCommentId = 1
    }
}