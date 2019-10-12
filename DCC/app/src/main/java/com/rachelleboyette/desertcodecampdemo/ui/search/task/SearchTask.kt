package com.rachelleboyette.desertcodecampdemo.ui.search.task

import android.os.AsyncTask
import android.util.Log
import com.rachelleboyette.desertcodecampdemo.MainApplication
import com.rachelleboyette.desertcodecampdemo.model.Cat
import org.joda.time.LocalDateTime
import org.joda.time.Seconds

class SearchTask(private val limit: Int? = null) : AsyncTask<Void, Void, List<Cat>>() {
    private lateinit var executed: LocalDateTime
    override fun onPreExecute() {
        // start the timer
        executed = LocalDateTime.now()
        Log.d(javaClass.name, "Async task started at $executed")

    }
    override fun doInBackground(vararg p0: Void?): List<Cat> {
        return if (limit == null) {
            MainApplication.catSearchService.getBreeds()
        } else {
            MainApplication.catSearchService.getBreedsByLimit(limit)
        }
    }

    override fun onPostExecute(result: List<Cat>?) {
        val done = LocalDateTime.now()
        Log.d(javaClass.name, "Async task started at $executed and ended at $done")
        Log.d(javaClass.name, "Total time it took: ${Seconds.secondsBetween(executed, done).seconds} seconds")
        //end the timer, send log message
    }
}