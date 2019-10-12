package com.rachelleboyette.desertcodecampdemo.ui.search.viewmodel

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rachelleboyette.desertcodecampdemo.MainApplication
import com.rachelleboyette.desertcodecampdemo.MainApplication.Companion.ASYNC_TASK
import com.rachelleboyette.desertcodecampdemo.MainApplication.Companion.COROUTINE
import com.rachelleboyette.desertcodecampdemo.MainApplication.Companion.EXECUTOR
import com.rachelleboyette.desertcodecampdemo.MainApplication.Companion.SYNCHRONOUS
import com.rachelleboyette.desertcodecampdemo.model.Cat
import com.rachelleboyette.desertcodecampdemo.model.dto.CatDto
import com.rachelleboyette.desertcodecampdemo.service.CatImageService
import com.rachelleboyette.desertcodecampdemo.service.CatSearchService
import com.rachelleboyette.desertcodecampdemo.ui.search.task.SearchTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.joda.time.LocalDateTime
import org.joda.time.Seconds
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor

class ViewAllCatsViewModel : ViewModel() {
    private var state: Int = -1
    private var data: MutableLiveData<ViewState> = MutableLiveData()
    private var catClickedData: MutableLiveData<Cat> = MutableLiveData()

    init {
        data.value = ViewState(true, null)
    }

    fun onCatBreedClicked(cat: Cat) {
        catClickedData.postValue(cat)
    }

    fun setState(state: Int) {
        this.state = state
        update()
    }

    fun getData(): LiveData<ViewState> {
        return data
    }

    fun getCatClickedData() : LiveData<Cat> {
        return catClickedData
    }

    private fun update() {
        when (state) {
            SYNCHRONOUS -> loadDataSynchronously()
            ASYNC_TASK -> {
                viewModelScope.launch(Dispatchers.Main) {  loadDataWithAsyncTask() }
                 // You can do this without coroutines with an executor too
//                loadDataWithAsyncTask()
            }
            COROUTINE -> viewModelScope.launch(Dispatchers.Main) { loadDataWithCoroutines() }
            EXECUTOR -> loadDataWithExecutors()
            else -> {
                // Do nothing, we shouldn't reach this
                Log.d(javaClass.name, "Unknown State")
            }
        }
    }

    private  fun loadDataWithExecutors() {
        val executed = LocalDateTime.now()

        val executor: Executor = AsyncTask.THREAD_POOL_EXECUTOR
        executor.execute {
            val cats = MainApplication.catSearchService.getBreeds()

            data.postValue(ViewState(false, cats))

            val done = LocalDateTime.now()

            Log.d(javaClass.name, "Executor ended")
            Log.d(javaClass.name, "Time it took between $executed and $done was ${Seconds.secondsBetween(executed, done).seconds} seconds")
        }
    }

    private suspend fun loadDataWithCoroutines() {
        val executed = LocalDateTime.now()

        viewModelScope.launch(Dispatchers.IO) {
            // First style of writing async-await
            val cats = async { MainApplication.catSearchService.getBreeds() }
            data.postValue(ViewState(false, cats.await()))


            //This is the same
//             val cats = async { MainApplication.catSearchService.getBreeds() }.await()
//             data.postValue(ViewState(false, cats))

            // OR
            //val cats = withContext(Dispatchers.Default) { MainApplication.catSearchService.getBreeds() }
            // data.postValue(ViewState(false, cats)

            val done = LocalDateTime.now()

            Log.d(javaClass.name, "Coroutine ended")
            Log.d(javaClass.name, "Time it took between $executed and $done was ${Seconds.secondsBetween(executed, done).seconds} seconds")
        }

    }

    private fun loadDataWithAsyncTask() {
        // Attempt to load data with async task

        //Uses the coroutine way to launch a job on IO thread
        viewModelScope.launch(Dispatchers.IO) {
            val task = SearchTask()
            task.execute()
            val cats = task.get()

            data.postValue(ViewState(false, cats))
        }

        //OR
        //Use execute with runnable to launch
        //Execute method creates its own thread for async task to run on
//        val task = SearchTask(500)
//
//        val cats = task.execute().get()
//        data.postValue(ViewState(false, cats))
    }

    private fun loadDataSynchronously() {
        val executed = LocalDateTime.now()
        // Attempt to load data synchronously

        // Will this throw an exception? Maybe
        // This will attempt to return ~500+ results
        val retrofit = Retrofit.Builder()
            .baseUrl(MainApplication.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(CatSearchService.Service::class.java)
        val call = service.getBreeds()

        val response = call.execute()
        if (response.isSuccessful) {
            response.body()?.let {
                val cats = CatDto.fromDto(it)

                data.postValue(ViewState(false, cats))
            }
        }

//        val cats = MainApplication.catSearchService.getBreeds()

        // Limit the search
//        val cats = MainApplication.catSearchService.getBreedsByLimit(250)

//        data.value = ViewState(false, cats)

    }

    class ViewState(
        val isLoading: Boolean,
        val data: List<Cat>?
    )
}