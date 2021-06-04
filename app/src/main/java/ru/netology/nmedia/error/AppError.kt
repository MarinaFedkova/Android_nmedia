package ru.netology.nmedia.error

import ru.netology.nmedia.R
import java.lang.RuntimeException

sealed class AppError(var code: String): RuntimeException()
class ApiError(val status: Int, code: String): AppError(code)
object NetworkError : AppError("${R.string.network_error}")
object UnknownError: AppError("${R.string.error_unknown}")