package ru.netology.nmedia.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl

class RemovePostWorker(
    appllicationContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appllicationContext, params) {
    companion object {
        const val name = "ru.netology.work.RemovePostsWorker"
    }

    override suspend fun doWork(): Result {
        val id = inputData.getLong(name, 0L)
        if (id == 0L) {
            return Result.failure()
        }
        val repository: PostRepository =
            PostRepositoryImpl(
                AppDb.getInstance(context = applicationContext).postDao(),
                AppDb.getInstance(context = applicationContext).postWorkDao(),
            )
        return try {
            repository.removeById(id)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}