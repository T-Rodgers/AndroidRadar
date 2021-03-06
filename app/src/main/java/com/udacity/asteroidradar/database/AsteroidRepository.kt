package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.API_KEY
import com.udacity.asteroidradar.api.AsteroidApiService
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.utils.getCurrentDay
import com.udacity.asteroidradar.utils.getWeekFromCurrentDay
import com.udacity.asteroidradar.models.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroidsOfCurrentWeek: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroidsOfWeek(getCurrentDay(), getWeekFromCurrentDay())){
            it.asDomainModel()
        }
    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()){
            it.asDomainModel()
        }

    val currentDayAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getCurrentDayAsteroids(getCurrentDay())){
            it.asDomainModel()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val response = AsteroidApiService.AsteroidApi.retrofitService.getAsteroidsAsync(
                getCurrentDay(),
                getWeekFromCurrentDay(),
                API_KEY
            ).await()

            val asteroidList = parseAsteroidsJsonResult(JSONObject(response))
            database.asteroidDao.insertAll(*asteroidList.asDatabaseModel())
        }
    }
}