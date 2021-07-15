package ru.netology.nmedia.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.netology.nmedia.api.ApiService
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.error.ApiError

class PostPagingSource(
    private val service: ApiService,
) : PagingSource<Long, Post>() {
    override fun getRefreshKey(state: PagingState<Long, Post>): Long? {
        return null
    }

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, Post> {
        try {
            val response = when (params) {
                is LoadParams.Refresh -> service.getLatest(params.loadSize)
                is LoadParams.Append -> service.getBefore(params.key, params.loadSize)
                is LoadParams.Prepend -> service.getAfter(params.key, params.loadSize)
            }
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val posts = response.body() ?: throw ApiError(response.code(), response.message())

            return LoadResult.Page(
                data = posts,
                prevKey = params.key,
                nextKey = posts.lastOrNull()?.id
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}