package com.udacity.shoestore

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.get
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.databinding.ActivityMainBinding
import timber.log.Timber

/**
 * Created by Jorge Alberto Gonzalez
 * */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var viewModel: ShoeStoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        viewModel = ViewModelProvider(this).get(ShoeStoreViewModel::class.java)
        Timber.plant(Timber.DebugTree())
        setUpViews()
    }

    private fun setUpViews() {
        setSupportActionBar(binding.toolbar)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        // Set the login and show list fragments as top destinations
        val topLevelDestinations: MutableSet<Int> = HashSet()
        topLevelDestinations.add(R.id.loginFragment)
        topLevelDestinations.add(R.id.shoeListFragment)
        appBarConfiguration = AppBarConfiguration.Builder(topLevelDestinations)
            .build()

        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)

        // Show the floating action button when the shoe list fragment is on screen
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.addButton.visibility = if (destination == controller.graph[R.id.shoeListFragment]) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        // Navigate to the show detail fragment when the FAB is clicked
        binding.addButton.setOnClickListener {
            navController.navigate(R.id.action_shoeListFragment_to_shoeDetailFragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, this.findNavController(R.id.nav_host_fragment)) ||
                super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (navController.currentDestination != navController.graph.findNode(R.id.shoeListFragment)) {
            super.onBackPressed()
        } else {
            finish()
        }
    }
}
