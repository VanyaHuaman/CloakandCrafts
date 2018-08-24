package cloakandcrafts.com.cloakandcrafts.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import cloakandcrafts.com.cloakandcrafts.Fragments.CocktailsFragment
import cloakandcrafts.com.cloakandcrafts.Fragments.SpeakeasyFragment
import cloakandcrafts.com.cloakandcrafts.Fragments.WithFoodFragment

class SectionPagerAdapter(fragmentManager : FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {CocktailsFragment()}
            1 -> {WithFoodFragment()}
            else -> {SpeakeasyFragment()}
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position){
            0 -> return "C"
            1 -> return "F"
            2 -> return "S"
            else -> return null
        }
    }

}