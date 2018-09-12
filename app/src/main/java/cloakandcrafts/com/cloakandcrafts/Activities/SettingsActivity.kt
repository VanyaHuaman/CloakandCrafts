package cloakandcrafts.com.cloakandcrafts.Activities

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.SeekBar
import cloakandcrafts.com.cloakandcrafts.R
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    var miles:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        settings_seekBar.setOnSeekBarChangeListener(this)
        miles = getPrefMiles()
        settings_amountMiles.setText(miles)
        settings_seekBar.progress = miles
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        settings_amountMiles.setText(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        setPrefMiles(seekBar.progress)
    }

    fun setPrefMiles(value:Int){
        //sets the miles value in Preferences
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putInt("miles", value)
                    .apply()
        }
        Log.i("Settings", "Miles saved to pref: $value")
    }

    fun getPrefMiles():Int{
        //returns the miles value from Preferences
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val prefMiles:Int = sharedPref.getInt("miles", 99)
        Log.i("distance","Retrieved Miles: $prefMiles")
        return prefMiles
    }
}