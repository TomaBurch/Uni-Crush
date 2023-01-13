package com.example.findyourpair.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.findyourpair.R
import com.example.findyourpair.adapters.ViewPagerFragmentAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainAppFragment:Fragment(R.layout.fragment_main_app) {

    private lateinit var viewPager2: ViewPager2
    private lateinit var appBar: Toolbar
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_app, container, false)

        val fragmentList = arrayListOf<Fragment>(FavouritesFragment(), ChatFragment(), ProfileFragment(), SettingsFragment())
        val adapter = ViewPagerFragmentAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewPager2 = view.findViewById(R.id.viewPager2)
        bottomNavigation = view.findViewById(R.id.bottomNavigation)
        appBar = view.findViewById(R.id.app_bar)

        appBar.inflateMenu(R.menu.settings_menu)
        appBar.setOnMenuItemClickListener {
            if(it.itemId == R.id.settings){
                MainAppFragmentDirections.actionMainAppFragmentToSettingsFragment().also { nav ->
                    findNavController().navigate(nav)
                }
                true
            }else{
                false
            }
        }

        bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.favourites -> {
                    viewPager2.currentItem = 0
                    return@setOnItemSelectedListener true
                }
                R.id.chat -> {
                    viewPager2.currentItem = 1
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}