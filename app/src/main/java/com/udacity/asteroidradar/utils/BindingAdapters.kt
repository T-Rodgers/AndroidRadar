package com.udacity.asteroidradar.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.main.AsteroidApiStatus
import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.models.AsteroidsAdapter
import com.udacity.asteroidradar.models.PictureOfDay

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription =
            imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription =
            imageView.context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription =
            imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription =
            imageView.context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("codeName")
fun TextView.setCodeName(asteroid: Asteroid?) {
    text = asteroid?.let { asteroid.codename }
}

@BindingAdapter("asteroidList")
fun bindRecyclerView(recyclerView: RecyclerView, asteroids: List<Asteroid>?) {

    val adapter = recyclerView.adapter as AsteroidsAdapter
    asteroids?.let {
        adapter.submitList(asteroids)
        recyclerView.scheduleLayoutAnimation()
    }

}

@BindingAdapter("status")
fun bindApiStatus(progressBar: ProgressBar, status: AsteroidApiStatus?) {
    when (status) {
        AsteroidApiStatus.LOADING -> {
            progressBar.visibility = View.VISIBLE
        }
        AsteroidApiStatus.ERROR -> {
            progressBar.visibility = View.GONE

        }
        AsteroidApiStatus.DONE -> {
            progressBar.visibility = View.GONE
        }
    }
}

@BindingAdapter("pictureOfTheDay")
fun bindPictureOfTheDay(imageView: ImageView, picture: PictureOfDay?) {
    if (picture == null) {
        imageView.contentDescription =
            imageView.context.getString(R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet)
    } else {
        Glide.with(imageView.context)
            .load(picture.url)
            .placeholder(R.drawable.placeholder_picture_of_day)
            .error(R.drawable.ic_baseline_error_48)
            .into(imageView)
        imageView.contentDescription =
            imageView.context.getString(R.string.nasa_picture_of_day_content_description_format, picture.title)
    }
}
