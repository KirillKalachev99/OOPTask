package ru.netology.wall_reports

class ReportComment(
    val ownerId: Int,
    val commentId: Int,
    val reason: Int
) {
    val reasonsList: Array<String> = arrayOf(
        "спам",
        "детская порнография",
        "экстремизм",
        "насилие",
        "пропаганда наркотиков",
        "материал для взрослых",
        "оскорбление",
        "призывы к суициду"
    )
}