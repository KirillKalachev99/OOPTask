package ru.netology

fun main() {
    val wall1 = WallService
    val comment1 = Comment(10)
    val likes1 = Like(200)

    val post1 = Post(2, 3, 5052025, "Текст первого поста!", 4, 5, true, comment1, likes1)
    val post2 = Post(2, 3, 5052025, "Обновленный текст первого поста!", 4, 5, true, comment1, likes1)
    val post3 = Post(3, 4, 6052025, "Новый текст поста! №1", 4, 5, true, comment1, likes1)
    val post4 = Post(4, 5, 6052025, "Новый текст поста! №2", 4, 5, true, comment1, likes1)
    val post5 = Post(5, 6, 6052025, "Новый текст поста! №3", 4, 5, true, comment1, likes1)

    wall1.add(post1)
    wall1.add(post2)
    wall1.add(post3)
    wall1.add(post4)
    wall1.add(post5)

    val postForUpdate = Post(5, 6, 6052025, "Обновленный текст второго поста!", 4, 5, true, comment1, likes1, id = 2)

    wall1.update(postForUpdate)

    println(wall1.toString())
}