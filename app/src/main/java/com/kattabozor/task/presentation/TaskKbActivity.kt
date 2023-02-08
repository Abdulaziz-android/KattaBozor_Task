package com.kattabozor.task.presentation

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.kattabozor.task.R
import com.kattabozor.task.databinding.ActivityTaskKbBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskKbActivity : AppCompatActivity() , TaskKbView{

    private lateinit var binding: ActivityTaskKbBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityTaskKbBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_task_kb)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_offer, R.id.nav_history
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun showNavView() {
        binding.navView.visibility = View.VISIBLE
    }

    override fun hideNavView() {
        binding.navView.visibility = View.GONE
    }

}