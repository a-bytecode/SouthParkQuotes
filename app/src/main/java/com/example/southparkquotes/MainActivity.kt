package com.example.southparkquotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.southparkquotes.databinding.ActivityMainBinding
import com.example.southparkquotes.model.MainViewModel



class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.myDrawerLayout
        navController = Navigation.findNavController(this, R.id.sp_nav_host_fragment)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java) // ViewModel initialisieren

        binding.apply {

            toggle = ActionBarDrawerToggle(
                this@MainActivity,
                drawerLayout,
                R.string.nav_open,
                R.string.nav_close
            )
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)

            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_random_mode,
                    R.id.nav_home,
                    R.id.character_mode
                ),
                drawerLayout
            )

            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)

            navView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    // Hier findet die Navigation zwischen den Fragmenten statt.
                    R.id.nav_random_mode -> {
                        navController.navigate(R.id.quotesMenuFragment)
                    }
                    R.id.nav_home -> {
                        navController.navigate(R.id.homeFragment)
                    }
                    R.id.character_mode -> {
                        navController.navigate(R.id.menuFragment)
                    }
                    R.id.nav_logout -> {
                        viewModel.createEndDialog(this@MainActivity) {
                            navController.navigate(navController.currentDestination!!.id)
                        }
                    }
                }
                // Das schließen der Schublade nach der Navigation
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

