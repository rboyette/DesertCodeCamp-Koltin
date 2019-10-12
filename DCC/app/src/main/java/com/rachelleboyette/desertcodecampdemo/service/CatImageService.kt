package com.rachelleboyette.desertcodecampdemo.service

import com.rachelleboyette.desertcodecampdemo.model.CatImage
import com.rachelleboyette.desertcodecampdemo.model.dto.CatImageDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

class CatImageService(private val service: Service) {

    fun getCatImageById(imageId: String?): CatImage? {
        if (imageId != null) {
            val response = service.getCatImageById(imageId).execute()
            if (response.isSuccessful) {
                response.body()?.let {
                    return CatImageDto.fromDto(it[0])
                }
            }
        }
        return null
    }

    interface Service {
        //You'll need to get an api key
//        @Headers("x-api-key:<your-api-key>")
        @GET("images/search")
        fun getCatImageById(@Query("breed_ids") imageId: String): Call<List<CatImageDto>>
    }
}