package com.example.fitness_runnting_tracker

import android.content.Intent
import android.os.Bundle

import android.view.View

import androidx.appcompat.app.AppCompatActivity

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fitness_runnting_tracker.databinding.ActivityMainBinding
import com.example.fitness_runnting_tracker.other.Constants.Companion.ACTION_SHOW_TRACKING_FRAGMENT

import dagger.hilt.android.AndroidEntryPoint

import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var name: String
    lateinit var navHostFragment:NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.rootView)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController= navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)



        navigateToTrackingFragmentIfNeeded(intent)
        setSupportActionBar(binding.toolbar)

        if(name.isNotEmpty()) {
            val toolbarTitle = "Let's go, $name!"
            binding.tvToolbarTitle?.text = toolbarTitle
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.settingsFragment, R.id.runFragment2, R.id.statisticsFragment ->
                    binding.bottomNavigationView.visibility= View.VISIBLE
                else -> binding.bottomNavigationView.visibility= View.GONE
            }

        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        navigateToTrackingFragmentIfNeeded(intent)
    }
    private fun navigateToTrackingFragmentIfNeeded(intent: Intent?) {

        if(intent?.action == ACTION_SHOW_TRACKING_FRAGMENT) {
            //navHostFragment.findNavController().navigate(R.id.actionGlobalTrackingFragment)
            val navController= navHostFragment.navController
            navController.navigate(R.id.action_global_trackingFragment)
        }
    }

}