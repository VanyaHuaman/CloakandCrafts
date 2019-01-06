package cloakandcrafts.com.cloakandcrafts.Fragments

import android.app.Activity


class SpeakeasyFragment : LocationFragment() {
    override var parentActivity: Activity
        get() = this.requireActivity()
        set(value) {}
    override var fragmentName: String
        get() = "speakeasy"
        set(value) {}

}