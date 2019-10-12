package com.rachelleboyette.desertcodecampdemo.service

import android.util.Log
import com.rachelleboyette.desertcodecampdemo.model.Cat
import com.rachelleboyette.desertcodecampdemo.model.dto.CatDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

class CatSearchService(private val service: Service) {

    fun getBreeds(): List<Cat> {
        val response = service.getBreeds().execute()
        if (response.isSuccessful) {

            response.body().let {
                return CatDto.fromDto(it)
            }
        }
        return listOf()
    }

    fun getBreedById(id: String): Cat {
        val response = service.getBreed(id).execute()

        if (response.isSuccessful) {
            response.body().let {
                return CatDto.fromDto(it)
            }
        }
        return Cat()
    }

    fun getBreedsByLimit(limit: Int): List<Cat> {
        val response = service.getBreedsByLimit(limit).execute()

        if (response.isSuccessful) {
            response.body().let {
                return CatDto.fromDto(it)
            }
        }
        return listOf()
    }

    interface Service {
        //You'll need to sign up with the Cat API for a token
//        @Headers("x-api-key:<your-api-key>")
        @GET("breeds")
        fun getBreeds(): Call<List<CatDto>>

//        @Headers("x-api-key:<your-api-key>")
        @GET("breeds")
        fun getBreed(@Query("id") id: String): Call<CatDto>

//        @Headers("x-api-key:<your-api-key>")
        @GET("breeds")
        fun getBreedsByLimit(@Query("limit") limit: Int): Call<List<CatDto>>
    }
}