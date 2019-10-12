package com.rachelleboyette.desertcodecampdemo.ui.result.task

import android.os.AsyncTask
import android.util.Log
import com.rachelleboyette.desertcodecampdemo.MainApplication
import com.rachelleboyette.desertcodecampdemo.model.CatImage
import org.joda.time.LocalDateTime
import org.joda.time.Seconds

class ImageSearchTask(private val imageId: String?) : AsyncTask<String, Void, CatImage?>() {
    private lateinit var executed: LocalDateTime

    override fun onPreExecute() {
        executed = LocalDateTime.now()
        Log.d(javaClass.name, "Async task started at $executed")
    }

    override fun doInBackground(vararg p0: String?): CatImage? {
        if (imageId != null) {
            return MainApplication.catImageService.getCatImageById(imageId)
        }
        return null
    }

    override fun onPostExecute(result: CatImage?) {
        val done = LocalDateTime.now()
        Log.d(javaClass.name, "Async task started at $executed and ended at $done")
        Log.d(javaClass.name, "Total time it took: ${Seconds.secondsBetween(executed, done).seconds} seconds")
    }
}