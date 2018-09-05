package cloakandcrafts.com.cloakandcrafts.Activities

import android.content.Intent
import android.net.Uri
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
            R.id.action_settings -> {
                openSettings()
                return true
            }
            R.id.action_contact -> {
                openEmail()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openEmail() {
        val emailAddress= arrayOf("Contact@cloakandcrafts.com")
        val emailSubject = "Cloak & Crafts"

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL,emailAddress)
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    fun openSettings(){
        val intent: Intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }
}