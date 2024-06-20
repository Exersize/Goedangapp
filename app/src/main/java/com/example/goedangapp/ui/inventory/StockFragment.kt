package com.example.goedangapp.ui.inventory


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.goedangapp.ViewModelFactory
import com.example.goedangapp.databinding.FragmentStockBinding
import com.example.goedangapp.databinding.FragmentStockInputBinding

import com.example.goedangapp.ui.input.StockInputViewModel

import com.example.goedangapp.util.PagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class StockFragment : Fragment() {
    private var _binding: FragmentStockBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PagerAdapter
    private val fragments = listOf(
        CurrentStock(),
        LowInStock(),
        RecentAdd(),
    )

    private val viewModel: StockViewModel by viewModels<StockViewModel>{
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStockBinding.inflate(inflater, container, false)
        setupViewPagerAndTabs()
        return binding.root
    }

    private fun setupViewPagerAndTabs() {
        adapter = PagerAdapter(childFragmentManager, lifecycle, fragments)
        binding.viewPager2.adapter = adapter

        TabLayoutMediator(binding.stockTab, binding.viewPager2) { tab, position ->
            tab.text = when (position) {
                0 -> "Current Stock"
                1 -> "Low In Stock"
                2 -> "Recently Add"
                else -> "Tab $position"
            }
        }

        binding.stockTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager2.currentItem = tab.position
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

        })
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(   position)
                binding.stockTab.selectTab(binding.stockTab.getTabAt(position))
            }
        })
}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}