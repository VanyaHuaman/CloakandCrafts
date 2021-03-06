package cloakandcrafts.com.cloakandcrafts.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cloakandcrafts.com.cloakandcrafts.Fragments.SpeakeasyFragment
import cloakandcrafts.com.cloakandcrafts.R

class SpeakeasyActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_section)
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, SpeakeasyFragment())
                .commit()
    }
}