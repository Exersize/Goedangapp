package com.example.goedangapp.ui.sales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.goedangapp.databinding.FragmentSalesBinding
import com.example.goedangapp.util.PagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SalesFragment : Fragment() {
    private var _binding: FragmentSalesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PagerAdapter
    private val fragments = listOf(
        SalesActualFragment(),
        SalesPredictionFragment(),
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSalesBinding.inflate(inflater, container, false)
        setupViewPagerAndTabs()
        return binding.root
    }

    private fun setupViewPagerAndTabs() {
        adapter = PagerAdapter(childFragmentManager, lifecycle, fragments)
        binding.viewPager2.adapter = adapter

        TabLayoutMediator(binding.salesTab, binding.viewPager2) { tab, position ->
            tab.text = when (position) {
                0 -> "Actual"
                1 -> "Prediction"
                else -> "Tab $position"
            }
        }

        binding.salesTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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
                super.onPageSelected(position)
                binding.salesTab.selectTab(binding.salesTab.getTabAt(position))
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}