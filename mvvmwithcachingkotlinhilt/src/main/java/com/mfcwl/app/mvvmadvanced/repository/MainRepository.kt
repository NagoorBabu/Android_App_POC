package com.mfcwl.app.mvvmadvanced.repository

import com.mfcwl.app.mvvmadvanced.model.Blog
import com.mfcwl.app.mvvmadvanced.retrofit.BlogRetrofit
import com.mfcwl.app.mvvmadvanced.retrofit.NetworkMapper
import com.mfcwl.app.mvvmadvanced.room.BlogDao
import com.mfcwl.app.mvvmadvanced.room.CacheMapper
import com.mfcwl.app.mvvmadvanced.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class MainRepository
constructor(
    private val blogDao: BlogDao,
    private val blogRetrofit: BlogRetrofit,
    private var cacheMapper: CacheMapper,
    private var networkMapper: NetworkMapper
) {
    suspend fun getBlog(): Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        try {
            val networkBlogs = blogRetrofit.get()
            val blogs = networkMapper.mapFromEntityList(networkBlogs)
            for (blog in blogs) {
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }
            val cachedBlogs = blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlogs)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

}