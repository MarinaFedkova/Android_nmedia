package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import java.text.DecimalFormat

class PostRepositorySQLiteImpl(
    private val dao: PostDao,
) : PostRepository {
    private var posts = emptyList<Post>().reversed()

    private val data = MutableLiveData(posts)

    init {
        posts = dao.getAll()
        data.value = posts
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        dao.likeById(id)
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (!it.likedByMe) it.likes + 1 else it.likes - 1
            )
        }
        data.value = posts
    }

    override fun repostById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(reposts = it.reposts + 1)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        posts = if (id == 0L) {
            listOf(saved) + posts
        } else {
            posts.map {
                if (it.id != id) it else saved
            }
        }
        data.value = posts
    }
//    override fun save(post: Post) {
//        if (post.id == 0L) {
//            posts = listOf(
//                post.copy(
//                    id = nextId++,
//                    author = "Me",
//                    published = "now",
//                    likedByMe = false,
//                    likes = 0,
//                    reposts = 0
//                )
//            ) + posts
//            data.value = posts
//            return
//        }
//        posts = posts.map {
//            if (it.id != post.id) it else it.copy(content = post.content)
//        }
//        data.value = posts
//    }

    override fun video() {
        data.value = posts
    }

}






