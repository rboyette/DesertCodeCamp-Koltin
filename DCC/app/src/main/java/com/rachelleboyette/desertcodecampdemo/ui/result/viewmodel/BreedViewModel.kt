package com.rachelleboyette.desertcodecampdemo.ui.result.viewmodel

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rachelleboyette.desertcodecampdemo.MainApplication
import com.rachelleboyette.desertcodecampdemo.model.Cat
import com.rachelleboyette.desertcodecampdemo.ui.result.task.ImageSearchTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.LocalDateTime
import org.joda.time.Seconds
import java.util.concurrent.Executor

class BreedViewModel(private val cat: Cat?) : ViewModel() {
    private var state = -1
    private val data: MutableLiveData<ViewState> = MutableLiveData()

    init {
        data.postValue(ViewState(true, null, null))
    }

    fun getData() : LiveData<ViewState> {
        return data
    }

    fun setState(state: Int) {
        this.state = state
        update()
    }

    private fun update() {
        when (state) {
            MainApplication.SYNCHRONOUS -> loadDataSynchronously()
            MainApplication.ASYNC_TASK -> {
                viewModelScope.launch(Dispatchers.Main) {  loadDataWithAsyncTask() }
                // You can do this without coroutines with an executor too
//                loadDataWithAsyncTask()
            }
            MainApplication.COROUTINE -> viewModelScope.launch(Dispatchers.Main) { loadDataWithCoroutines() }
            MainApplication.EXECUTOR -> loadDataWithExecutors()
            else -> {
                // Do nothing, we shouldn't reach this
                Log.d(javaClass.name, "Unknown State")
            }
        }
    }

    private fun loadDataWithExecutors() {
        val executed = LocalDateTime.now()

        val executor: Executor = AsyncTask.THREAD_POOL_EXECUTOR
        executor.execute {
            val imageData = MainApplication.catImageService.getCatImageById(cat?.id)

            data.postValue(ViewState(false, imageData?.url, cat))

            val done = LocalDateTime.now()

            Log.d(javaClass.name, "Executor ended")
            Log.d(javaClass.name, "Time it took between $executed and $done was ${Seconds.secondsBetween(executed, done).seconds} seconds")
        }
    }

    private fun loadDataWithCoroutines() {
        val executed = LocalDateTime.now()
        viewModelScope.launch(Dispatchers.IO) {
            // First style of writing async-await
            val imageData = async { MainApplication.catImageService.getCatImageById(cat?.id) }
            data.postValue(ViewState(false, imageData.await()?.url, cat))

            //This is the same
//             val imageData = async { MainApplication.catImageService.getCatImageById(cat?.id) }.await()
//             data.postValue(ViewState(false, imageData?.url, cat))

            // OR
//            val imageData = withContext(Dispatchers.Default) { MainApplication.catImageService.getCatImageById(cat?.id) }
//            data.postValue(ViewState(false, imageData?.url, cat))

            val done = LocalDateTime.now()

            Log.d(javaClass.name, "Coroutine ended")
            Log.d(javaClass.name, "Time it took between $executed and $done was ${Seconds.secondsBetween(executed, done).seconds} seconds")
        }
    }

    private fun loadDataWithAsyncTask() {
        // Attempt to load data with async task

        //Uses the coroutine way to launch a job on IO thread
//        viewModelScope.launch(Dispatchers.IO) {
//            val task = ImageSearchTask(cat?.id)
//            task.execute()
//            val imageData = task.get()
//
//            data.postValue(ViewState(false, imageData?.url, cat))
//        }

        //OR
        //Use execute with runnable to launch

        val task = ImageSearchTask(cat?.id)

        val imageData = task.execute().get()
        data.postValue(ViewState(false, imageData?.url, cat))

    }

    private fun loadDataSynchronously() {
        // Attempt to load data synchronously

        // Will this throw an exception? Maybe
        // This will attempt to return the image
        val imageData = MainApplication.catImageService.getCatImageById(cat?.id)

        data.postValue(ViewState(false, imageData?.url, cat))
    }

    class ViewState(val isLoading: Boolean,
                    val imageData: String?,
                    val cat: Cat?)
}