package cloakandcrafts.com.cloakandcrafts.Activities

import android.app.Activity
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
        settings_amountMiles.setText(miles.toString())
        settings_seekBar.setProgress(miles)

    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        settings_amountMiles.setText(progress.toString())
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        setPrefMiles(seekBar!!.progress)
    }

    fun setPrefMiles(value:Int){
        val sp = getSharedPreferences("prefs", Activity.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putInt("miles", value)
        editor.commit()

        Log.i("Settings", "Miles saved to pref: $value")
    }

    fun getPrefMiles():Int{
        val sp = getSharedPreferences("prefs", Activity.MODE_PRIVATE)
        val prefMiles:Int = sp.getInt("miles", 0)
        return prefMiles
    }

}