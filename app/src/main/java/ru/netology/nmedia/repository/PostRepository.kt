package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getAll(): List<Post>
    fun likeById(id: Long)
    fun repostById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
    fun video()
    fun getPostById(id: Long): Post
}