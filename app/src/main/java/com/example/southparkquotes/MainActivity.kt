package com.example.southparkquotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.southparkquotes.UI.HomeFragment
import com.example.southparkquotes.UI.MenuFragment
import com.example.southparkquotes.UI.QuotesMenuFragment
import com.example.southparkquotes.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.myDrawerLayout

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
            navView.setNavigationItemSelectedListener {
                when (it.itemId) {

                    R.id.nav_random_mode -> {
                        Toast.makeText(
                            this@MainActivity,"Random Mode",
                            Toast.LENGTH_LONG)
                            .show()
                        val quotesMenuFragment = QuotesMenuFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.nav_host_fragment, quotesMenuFragment)
                            .commit()
                        // Drawer wird nach der Navigation geschlossen.
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }

                    R.id.nav_home -> {
                        val homeMenuFragment = HomeFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.nav_host_fragment,homeMenuFragment)
                            .commit()
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }

                    R.id.character_mode -> {
                        val menuFragment = MenuFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.nav_host_fragment,menuFragment)
                            .commit()
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                }
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

