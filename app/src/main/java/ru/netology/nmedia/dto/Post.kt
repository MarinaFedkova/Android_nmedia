package ru.netology.nmedia.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Post (
    val id: Long,
    val author: String,
    val authorAvatar: String?,
    val content: String,
    val published: String,
    var likedByMe: Boolean,
    val likes: Long,
    val reposts: Long,
    val videoUrl: String?,
    val wasRead: Boolean = false,
    val attachment: @RawValue Attachment?
): Parcelable