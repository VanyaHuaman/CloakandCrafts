package cloakandcrafts.com.cloakandcrafts.Fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cloakandcrafts.com.cloakandcrafts.Activities.MainActivity


class CocktailsFragment : LocationFragment() {

    override var parentActivity: Activity
        get() = this.requireActivity()
        set(value) {}

    override var fragmentName: String
        get() = "cocktails"
        set(value) {}

}