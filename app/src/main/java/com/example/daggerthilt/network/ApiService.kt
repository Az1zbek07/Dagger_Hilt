package com.example.daggerthilt.network

import com.example.daggerthilt.model.NoteResponse
import com.example.daggerthilt.model.NoteResponseItem
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getRemotePosts(): Response<List<NoteResponseItem>>
}