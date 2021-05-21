package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl
import ru.netology.nmedia.util.SingleLiveEvent

private val empty = Post(
    id = 0,
    author = "",
    authorAvatar = "",
    content = "",
    published = "",
    likedByMe = false,
    likes = 0,
    reposts = 0,
    videoUrl = "",
    attachment = null
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data
    val edited = MutableLiveData(empty)
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated
    private val _networkError = SingleLiveEvent<String>()
    val networkError: LiveData<String>
        get() = _networkError

    init {
        loadPosts()
    }

    fun loadPosts() {
        _data.value = FeedModel(loading = true)
        repository.getAll(object : PostRepository.Callback<List<Post>> {
            override fun onSuccess(value: List<Post>) {
                _data.postValue(FeedModel(posts = value, empty = value.isEmpty()))
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })
    }

    fun getPostById(id: Long) {

    }

    fun likeById(id: Long) {
        if (_data.value?.posts.orEmpty().filter { it.id == id }.none { it.likedByMe }) {
            repository.likeById(id, object : PostRepository.Callback<Post> {
                override fun onSuccess(value: Post) {
                    _data.postValue(
                        FeedModel(
                            posts = _data.value?.posts.orEmpty()
                                .map { if (it.id == value.id) value else it })
                    )
                }

                override fun onError(e: Exception) {
                    _networkError.value = e.message
                }
            })
        } else repository.dislikeById(id, object : PostRepository.Callback<Post> {
            override fun onSuccess(value: Post) {
                _data.postValue(
                    FeedModel(
                        posts = _data.value?.posts.orEmpty()
                            .map { if (it.id == value.id) value else it })
                )
            }

            override fun onError(e: Exception) {
                _networkError.value = e.message
            }
        })
    }

    fun removeById(id: Long) {
        repository.removeById(id, object : PostRepository.Callback<Unit> {
            override fun onSuccess(value: Unit) {
                val posts = _data.value?.posts.orEmpty()
                    .filter { it.id != id }
                _data.postValue(
                    _data.value?.copy(posts = posts, empty = posts.isEmpty())
                )
            }

            override fun onError(e: Exception) {
                _networkError.value = e.message
            }
        })
    }

    fun repostById(id: Long) = repository.repostById(id)
    fun video() = repository.video()


    fun save() {
        edited.value?.let {
            repository.save(it, object : PostRepository.Callback<Post> {
                override fun onSuccess(value: Post) {
//                    _data.postValue(_data.value?.posts?.let {
//                        FeedModel(posts = it.plus(value), empty = it.isEmpty())
//                    })
                    loadPosts()
                    _postCreated.value = Unit
                }

                override fun onError(e: Exception) {
                    _networkError.value = e.message
                     //edited.value
                }
            })
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }
}