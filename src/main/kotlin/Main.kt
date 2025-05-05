package ru.netology

fun main() {
    val comment1 = Comment(10)
    val likes1 = Like(200)

    val post1 = Post(1, 2, 3, 5052025, "Текст первого поста!", 4, 5, true, comment1, likes1)

    println(post1.likes)
    
}