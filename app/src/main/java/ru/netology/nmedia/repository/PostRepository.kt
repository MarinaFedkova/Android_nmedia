package ru.netology.nmedia.repository

import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun repostById(id: Long)
    fun video()

    fun getAll(callback: GetAllCallback)
    fun getPostById(id: Long, callback: PostCallBack)
    fun likeById(id: Long, callback: PostCallBack)
    fun dislikeById(id: Long, callback: PostCallBack)
    fun removeById(id: Long, callback: ByIdCallBack)
    fun save(post: Post, callback: PostCallBack)

    interface GetAllCallback {
        fun onSuccess(posts: List<Post>) {}
        fun onError(e: Exception) {}
    }

    interface ByIdCallBack {
        fun onSuccess() {}
        fun onError(e: Exception) {}
    }

    interface PostCallBack {
        fun onSuccess(post: Post) {}
        fun onError(e: Exception) {}
    }
}

