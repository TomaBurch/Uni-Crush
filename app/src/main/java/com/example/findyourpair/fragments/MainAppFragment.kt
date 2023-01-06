package com.example.findyourpair.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.findyourpair.R
import com.example.findyourpair.R.id
import com.example.findyourpair.adapters.ViewPagerFragmentAdapter
import com.example.findyourpair.databinding.FragmentMainAppBinding

class MainAppFragment:Fragment() {

    private var _binding : FragmentMainAppBinding ? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainAppBinding.inflate(inflater, container, false)
        val view = binding.root



        val fragmentList = arrayListOf<Fragment>(FavouritesFragment(), ChatFragment(), ProfileFragment(), SettingsFragment())

        val adapter = ViewPagerFragmentAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.appBar.inflateMenu(R.menu.settings_menu)
        binding.appBar.setOnMenuItemClickListener {
            if(it.itemId == R.id.settings){
                MainAppFragmentDirections.actionMainAppFragmentToSettingsFragment().also { nav ->
                    findNavController().navigate(nav)
                }
                true
            }else{
                false
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.favourites -> {
                    binding.viewPager2.currentItem = 0
                    return@setOnItemSelectedListener true
                }
                R.id.chat -> {
                    binding.viewPager2.currentItem = 1
                    return@setOnItemSelectedListener true
                }
                R.id.profile ->{
                    binding.viewPager2.currentItem = 2
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}