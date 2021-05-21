package ru.netology.nmedia.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.netology.nmedia.api.PostApi
import ru.netology.nmedia.dto.Post

class PostRepositoryImpl : PostRepository {

    override fun getAll(callback: PostRepository.Callback<List<Post>>) {
        PostApi.retrofitServise.getAll().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                } else {
                    callback.onSuccess(response.body().orEmpty())
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                callback.onError(RuntimeException(t))
            }
        })
    }

    override fun getPostById(id: Long, callback: PostRepository.Callback<Post>) {
        PostApi.retrofitServise.getPostById(id).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                } else {
                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(RuntimeException(t))
            }

        })
    }

    override fun likeById(id: Long, callback: PostRepository.Callback<Post>) {
        PostApi.retrofitServise.likeById(id).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                } else {
                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(RuntimeException(t))
            }

        })
    }

    override fun dislikeById(id: Long, callback: PostRepository.Callback<Post>) {
        PostApi.retrofitServise.dislikeById(id).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                } else {
                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(RuntimeException(t))
            }

        })
    }

    override fun removeById(id: Long, callback: PostRepository.Callback<Unit>) {
        PostApi.retrofitServise.removeById(id).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                } else {
                    callback.onSuccess(Unit)
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                callback.onError(RuntimeException(t))
            }

        })
    }

    override fun save(post: Post, callback: PostRepository.Callback<Post>) {
        PostApi.retrofitServise.save(post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                } else {
                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(RuntimeException(t))
            }

        })
    }

    override fun video() {
        //TODO
    }

    override fun repostById(id: Long) {
        //TODO
    }

}






