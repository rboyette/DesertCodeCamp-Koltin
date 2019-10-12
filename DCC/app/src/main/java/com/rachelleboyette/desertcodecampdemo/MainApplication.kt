package com.rachelleboyette.desertcodecampdemo

import android.app.Application
import com.rachelleboyette.desertcodecampdemo.model.Cat
import com.rachelleboyette.desertcodecampdemo.service.CatImageService
import com.rachelleboyette.desertcodecampdemo.service.CatSearchService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainApplication : Application() {

    companion object {
        lateinit var catSearchService: CatSearchService
        lateinit var catImageService: CatImageService
        const val BASE_URL = "https://api.thecatapi.com/v1/"

        const val ASYNC_TASK = 0
        const val SYNCHRONOUS = 1
        const val COROUTINE = 2
        const val EXECUTOR = 3

        var state: Int = SYNCHRONOUS
    }

    override fun onCreate() {
        super.onCreate()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val searchService = retrofit.create(CatSearchService.Service::class.java)
        val imageService =  retrofit.create(CatImageService.Service::class.java)
        catSearchService = CatSearchService(searchService)
        catImageService = CatImageService(imageService)

    }
}