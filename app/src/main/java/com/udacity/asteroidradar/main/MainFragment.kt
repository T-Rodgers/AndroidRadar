package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.models.AsteroidsAdapter
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = AsteroidsAdapter(AsteroidsAdapter.AsteroidItemClickListener {
           viewModel.onNavigateToDetails(it)
        })

        viewModel.navigateToDetails.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(
                    MainFragmentDirections.actionShowDetail(it)
                )
                viewModel.onDoneNavigating()
            }
        })

        binding.asteroidRecycler.adapter = adapter
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_today_asteroids -> viewModel.retrieveCurrentDayAsteroids()
            R.id.show_week_asteroids -> viewModel.retrieveAsteroidsOfWeek()
            R.id.show_saved_asteroids -> viewModel.retrieveAllAsteroids()
        }
        return true
    }
}
