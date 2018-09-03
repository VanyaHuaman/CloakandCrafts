package cloakandcrafts.com.cloakandcrafts.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import cloakandcrafts.com.cloakandcrafts.Fragments.WithFoodFragment
import cloakandcrafts.com.cloakandcrafts.R

class WithFoodActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_section)
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, WithFoodFragment())
                .commit()
    }
}