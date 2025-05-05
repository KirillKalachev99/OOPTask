package ru.netology

fun main() {
    val wall1 = WallService
    val comment1 = Comment(10)
    val likes1 = Like(200)

    val post1 = Post(1, 2, 3, 5052025, "Текст первого поста!", 4, 5, true, comment1, likes1)
    val post2 = Post(2, 2, 3, 5052025, "Обновленный текст первого поста!", 4, 5, true, comment1, likes1)
    wall1.add(post1)
    wall1.update(post2)

    println(wall1.toString())
}