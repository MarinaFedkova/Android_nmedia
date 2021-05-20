package ru.netology.nmedia.repository

import android.widget.Toast
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.netology.nmedia.api.PostApi
import ru.netology.nmedia.dto.Post
import java.io.IOException

class PostRepositoryImpl : PostRepository {

    override fun getAll(callback: PostRepository.GetAllCallback) {
        PostApi.retrofitServise.getAll().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    callback.onSuccess(response.body().orEmpty())
                } else {
                    when (response.code()) {
                        400 -> response.message()
                    }
                    // response.message() - статус сообщение ответа
                    callback.onError(RuntimeException(response.message()))
                    // response.code() - статус кода ответа
                    //response.errorBody() - raw-body ответа
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                callback.onError(RuntimeException(t))
            }
        })
    }

    override fun getPostById(id: Long, callback: PostRepository.PostCallBack) {
        PostApi.retrofitServise.getPostById(id).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                } else {
                    callback.onError(RuntimeException(response.message()))
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(RuntimeException(t))
            }

        })
    }

    override fun likeById(id: Long, callback: PostRepository.PostCallBack) {
        PostApi.retrofitServise.likeById(id).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                } else {
                    callback.onError(RuntimeException(response.message()))
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(RuntimeException(t))
            }

        })
    }
    /*val request: Request = Request.Builder()
        .post(gson.toJson(id).toRequestBody(jsonType))
        .url("${BASE_URL}/api/slow/posts/${id}/likes")
        .build()
    client.newCall(request)
        .enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string() ?: throw RuntimeException("body is null")
                try {
                    callback.onSuccess(gson.fromJson(body, Post::class.java))
                } catch (e: Exception) {
                    callback.onError(e)
                }                }
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e)
            }            })*/


    override fun dislikeById(id: Long, callback: PostRepository.PostCallBack) {
        PostApi.retrofitServise.dislikeById(id).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                } else {
                    callback.onError(RuntimeException(response.message()))
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(RuntimeException(t))
            }

        })
    }
    /*val request: Request = Request.Builder()
        .delete(gson.toJson(id).toRequestBody(jsonType))
        .url("${BASE_URL}/api/slow/posts/${id}/likes")
        .build()
    client.newCall(request)
        .enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string() ?: throw RuntimeException("body is null")
                try {
                    callback.onSuccess(gson.fromJson(body, Post::class.java))
                } catch (e: Exception) {
                    callback.onError(e)
                }                }
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e)
            }            })*/

    override fun removeById(id: Long, callback: PostRepository.ByIdCallBack) {
        PostApi.retrofitServise.removeById(id).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    callback.onSuccess()
                } else {
                    callback.onError(RuntimeException(response.message()))
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                callback.onError(RuntimeException(t))
            }

        })
    }
    /*val request: Request = Request.Builder()
        .delete()
        .url("${BASE_URL}/api/slow/posts/$id")
        .build()
    client.newCall(request)
        .enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string() ?: throw RuntimeException("body is null")
                try {
                    callback.onSuccess()
                } catch (e: Exception) {
>>>>>>> retrofit
                    callback.onError(e)
                }                }
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e)                }
        })*/

    override fun save(post: Post, callback: PostRepository.PostCallBack) {
        PostApi.retrofitServise.save(post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                } else {
                    callback.onError(RuntimeException(response.message()))
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(RuntimeException(t))
            }

        })
    }
    /*val request: Request = Request.Builder()
        .post(gson.toJson(post).toRequestBody(jsonType))
        .url("${BASE_URL}/api/slow/posts")
        .build()
    client.newCall(request)
        .enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string() ?: throw RuntimeException("body is null")
                try {
                    callback.onSuccess(gson.fromJson(body, Post::class.java))
                } catch (e: Exception) {
                    callback.onError(e)
                }                }
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e)                }            })*/

    override fun video() {
        //TODO
    }

    override fun repostById(id: Long) {
        //TODO
    }

}






