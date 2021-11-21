package com.udacity.asteroidradar.utils

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidRepository
import com.udacity.asteroidradar.database.getDatabase
import retrofit2.HttpException

class RefreshAsteroidsWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object{
        const val WORK_NAME = "RefreshAsteroidWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val androidRepository = AsteroidRepository(database)
        return try {
            androidRepository.refreshAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}