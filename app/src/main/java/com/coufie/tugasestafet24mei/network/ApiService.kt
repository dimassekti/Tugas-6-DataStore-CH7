package com.coufie.tugasestafet24mei.network

import com.coufie.tugasestafet24mei.model.GetUserItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //Login
    @GET("user")
    fun getUser(
        @Query("username") username : String
    ) : Call<List<GetUserItem>>
}