package com.app.cameraxwork.mvccamera.repository

import com.app.cameraxwork.mvccamera.model.Lpr
import com.app.cameraxwork.mvccamera.retrofit.LprRetrofit
import com.app.cameraxwork.mvccamera.room.CacheMapper
import com.app.cameraxwork.mvccamera.room.LprDao
import com.app.cameraxwork.mvccamera.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class LprRepository
constructor(
    private val lprDao: LprDao,
    private val lprRetrofit: LprRetrofit,
    private var cacheMapper: CacheMapper
) {
    suspend fun getOcrValue(file: File): Flow<DataState<List<Lpr>>> = flow {
        emit(DataState.Loading)
        try {

            val filePart = MultipartBody.Part.createFormData(
                "upload",
                file.getName(),
                file.asRequestBody("image/*".toMediaTypeOrNull())
            )
            val networkLpr = lprRetrofit.get(filePart)
            val ocrValue: String;
            if (networkLpr.results.size > 0) {
                ocrValue = networkLpr.results.get(0).plate.toString()
                val lpr = Lpr(file.absolutePath, "Registration Number", ocrValue, ocrValue)
                lprDao.insert(cacheMapper.mapToEntity(lpr))
                val cachedLprs = lprDao.get()
                emit(DataState.Success(cacheMapper.mapFromEntityList(cachedLprs)))
            } else {

            }

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

}