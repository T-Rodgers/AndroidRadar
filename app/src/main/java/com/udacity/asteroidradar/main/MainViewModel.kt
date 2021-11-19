package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.API_KEY
import com.udacity.asteroidradar.api.AsteroidApiService.AsteroidApi
import com.udacity.asteroidradar.database.AsteroidRepository
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.models.PictureOfDay
import kotlinx.coroutines.launch

enum class AsteroidApiStatus {
    LOADING, ERROR, DONE
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _status = MutableLiveData<AsteroidApiStatus>()
    val status: LiveData<AsteroidApiStatus>
        get() = _status

    private val _navigateToDetails = MutableLiveData<Asteroid?>()
    val navigateToDetails: MutableLiveData<Asteroid?>
        get() = _navigateToDetails

    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()
    val pictureOfTheDay: LiveData<PictureOfDay>
        get() = _pictureOfTheDay

    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)

    private val _asteroids = MutableLiveData<LiveData<List<Asteroid>>>()
    val asteroids: LiveData<LiveData<List<Asteroid>>>
        get() = _asteroids


    init {
        retrieveAsteroidsOfWeek()
        retrievePictureOfTheDay()
        viewModelScope.launch {

            try {
                asteroidRepository.refreshAsteroids()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onNavigateToDetails(asteroid: Asteroid) {
        _navigateToDetails.value = asteroid
    }

    fun onDoneNavigating() {
        _navigateToDetails.value = null
    }

    private fun retrievePictureOfTheDay() {
        viewModelScope.launch {
            try {
                _pictureOfTheDay.value = AsteroidApi.retrofitService.getPictureOfTheDay(API_KEY)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun retrieveAsteroidsOfWeek() {
        viewModelScope.launch {
            _status.value = AsteroidApiStatus.LOADING
            try {

                _asteroids.value = asteroidRepository.asteroidsOfCurrentWeek
                _status.value = AsteroidApiStatus.DONE

            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = AsteroidApiStatus.ERROR
            }
        }
    }

    fun retrieveCurrentDayAsteroids() {
        viewModelScope.launch {
            _status.value = AsteroidApiStatus.LOADING
            try {

                _asteroids.value = asteroidRepository.currentDayAsteroids
                _status.value = AsteroidApiStatus.DONE

            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = AsteroidApiStatus.ERROR
            }
        }
    }

    fun retrieveAllAsteroids() {
        viewModelScope.launch {
            _status.value = AsteroidApiStatus.LOADING
            try {

                _asteroids.value = asteroidRepository.asteroids
                _status.value = AsteroidApiStatus.DONE

            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = AsteroidApiStatus.ERROR
            }
        }
    }
}