package ru.netology.nmedia.dao

import ru.netology.nmedia.dto.Post
import java.io.Closeable

interface PostDao {
    fun getAll(): List<Post>
    fun likeById(id: Long)
    fun repostById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post): Post
    fun video()
}