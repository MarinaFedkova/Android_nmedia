package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likedByMe: Boolean,
    val likes: Long,
    val reposts: Long,
    val videoUrl: String?
) {
    companion object {
        fun fromDto(dto: Post) = with(dto) {
            PostEntity(
                id = id,
                author = author,
                content = content,
                published = published,
                likedByMe = likedByMe,
                likes = likes,
                reposts = reposts,
                videoUrl = videoUrl
            )
        }
    }
    fun toPost(): Post = Post(
        id = id,
        author = author,
        content = content,
        published = published,
        likedByMe = likedByMe,
        likes = likes,
        reposts = reposts,
        videoUrl = videoUrl
    )
}