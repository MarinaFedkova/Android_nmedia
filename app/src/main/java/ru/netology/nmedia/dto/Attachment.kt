package ru.netology.nmedia.dto

import android.media.Image

data class Attachment (
    val url: String,
    val description: String,
    val type: AttachmentType
        )

enum class AttachmentType {
    IMAGE
}