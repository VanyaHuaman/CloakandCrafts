package cloakandcrafts.com.cloakandcrafts.Fragments

import android.app.Activity


class WithFoodFragment : LocationFragment() {
    override var parentActivity: Activity
        get() = this.requireActivity()
        set(value) {}
    override var fragmentName: String
        get() = "withFood"
        set(value) {}

}