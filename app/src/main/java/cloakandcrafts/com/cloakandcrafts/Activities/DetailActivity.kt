package cloakandcrafts.com.cloakandcrafts.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import cloakandcrafts.com.cloakandcrafts.Fragments.DetailFragment
import cloakandcrafts.com.cloakandcrafts.R

class DetailActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.detail_container_layout)
        supportFragmentManager.beginTransaction()
                .replace(R.id.detail_container, DetailFragment())
                .commit()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}