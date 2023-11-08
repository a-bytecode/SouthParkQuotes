package com.example.southparkquotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.southparkquotes.UI.DetailQuoteFragmentDirections
import com.example.southparkquotes.UI.HomeFragmentDirections
import com.example.southparkquotes.UI.MenuFragmentDirections
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
            // Hier wird das Pfeil Symbol durch "true" angezeigt.
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            // Durch den addOnDestinationChangedListener legen wir das Custom-Symbol dauerhaft fest.
            navController.addOnDestinationChangedListener { _, _, _ ->
                // Hier legen wir ein custom icon fest für jedes Ziel-Fragment was abgerufen wird.
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
                supportActionBar?.setDisplayShowTitleEnabled(false) // Hier wird keine Überschrift im Titel festgelegt.
            }

            val imageResource = viewModel.repo.charList[viewModel.repo.charPick].imageResource
            viewModel.selectedImageResource = imageResource ?: R.drawable.stan_marsh_0 // Default Wert
            val selectedCharacter = viewModel.repo.charList[viewModel.repo.charPick]
            // damit Detailquotes erkennt das wir aus dem Quotes menu kommen.

            val myNav = HomeFragmentDirections.actionHomeFragmentToDetailQuoteFragment(
                imageResource!!,
                viewModel.goingRandomMode,
                selectedCharacter)

            val myNavDetail = DetailQuoteFragmentDirections.actionDetailQuoteFragmentSelf(
                imageResource,
                viewModel.goingRandomMode,
                selectedCharacter)

            val myNavMenu = MenuFragmentDirections.actionMenuFragmentToDetailQuoteFragment(
                imageResource,
                viewModel.goingRandomMode,
                selectedCharacter)


            navView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    // Hier findet die Navigation zwischen den Fragmenten statt.
                    R.id.nav_random_mode -> {
                        if (navController.currentDestination?.id == R.id.homeFragment) {
                            viewModel.goingRandomMode = true // Hier schalten wir goingRandomMode auf true
                            navController.navigate(myNav)
                        } else if (navController.currentDestination?.id == R.id.menuFragment){
                            Toast.makeText(
                                this@MainActivity,
                                "Random Mode",
                                Toast.LENGTH_LONG)
                                .show()
                            viewModel.goingRandomMode = true
                            navController.navigate(myNavMenu)
                        } else {
                            viewModel.goingRandomMode = true
                            Toast.makeText(
                                this@MainActivity,
                                "Random Mode",
                                Toast.LENGTH_LONG)
                                .show()
                            navController.navigate(myNavDetail)
                        }
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
                    R.id.nav_settings -> {
                        navController.navigate(R.id.settingsFragment)
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

