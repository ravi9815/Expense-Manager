package com.example.easykhatabahi.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.example.easykhatabahi.R
import com.example.easykhatabahi.databinding.FragmentStatsBinding
import com.example.easykhatabahi.models.Transaction
import com.example.easykhatabahi.utils.Constants
import com.example.easykhatabahi.utils.Constants.SELECTED_STATS_TYPE
import com.example.easykhatabahi.utils.Helper.formatDate
import com.example.easykhatabahi.utils.Helper.formatDateByMonth
import com.example.easykhatabahi.viewmodels.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import io.realm.RealmResults
import java.util.Calendar

class StatsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var binding: FragmentStatsBinding
    lateinit var calendar: Calendar

    /*
    0 = Daily
    1 = Monthly
     */
    var viewModel: MainViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatsBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        calendar = Calendar.getInstance()
        updateDate()
        binding.incomeBtn.setOnClickListener { view ->
            binding.incomeBtn.setBackground(requireContext().getDrawable(R.drawable.income_selector))
            binding.expenseBtn.setBackground(requireContext().getDrawable(R.drawable.default_selector))
            binding.expenseBtn.setTextColor(requireContext().getColor(R.color.textColor))
            binding.incomeBtn.setTextColor(requireContext().getColor(R.color.greenColor))
            SELECTED_STATS_TYPE = Constants.INCOME
            updateDate()
        }
        binding.expenseBtn.setOnClickListener { view ->
            binding.incomeBtn.setBackground(requireContext().getDrawable(R.drawable.default_selector))
            binding.expenseBtn.setBackground(requireContext().getDrawable(R.drawable.expense_selector))
            binding.incomeBtn.setTextColor(requireContext().getColor(R.color.textColor))
            binding.expenseBtn.setTextColor(requireContext().getColor(R.color.redColor))
            SELECTED_STATS_TYPE = Constants.EXPENSE
            updateDate()
        }
        binding.nextDateBtn.setOnClickListener { c ->
            if (Constants.SELECTED_TAB_STATS == Constants.DAILY) {
                calendar.add(Calendar.DATE, 1)
            } else if (Constants.SELECTED_TAB_STATS == Constants.MONTHLY) {
                calendar.add(Calendar.MONTH, 1)
            }
            updateDate()
        }
        binding.previousDateBtn.setOnClickListener { c ->
            if (Constants.SELECTED_TAB_STATS == Constants.DAILY) {
                calendar.add(Calendar.DATE, -1)
            } else if (Constants.SELECTED_TAB_STATS == Constants.MONTHLY) {
                calendar.add(Calendar.MONTH, -1)
            }
            updateDate()

        }
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.text == "Monthly") {
                    Constants.SELECTED_TAB_STATS = 1
                    updateDate()
                } else if (tab.text == "Daily") {
                    Constants.SELECTED_TAB_STATS = 0
                    updateDate()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        val pie = AnyChart.pie()
        viewModel!!.categoriesTransactions.observe(
            viewLifecycleOwner,
            object : Observer<RealmResults<Transaction>?> {
                override fun onChanged(transactions: RealmResults<Transaction>?) {
                    if (transactions!!.size > 0) {
                        binding.emptyState.setVisibility(View.GONE)
                        binding.anyChart.setVisibility(View.VISIBLE)
                        val data: MutableList<DataEntry> = ArrayList()
                        val categoryMap: MutableMap<String?, Double> = HashMap()
                        for (transaction in transactions) {
                            val category = transaction.category
                            val amount = transaction.amount
                            if (categoryMap.containsKey(category)) {
                                var currentTotal = categoryMap[category]!!
                                currentTotal += Math.abs(amount)
                                categoryMap[category] = currentTotal
                            } else {
                                categoryMap[category] = Math.abs(amount)
                            }
                        }
                        for ((key, value) in categoryMap) {
                            data.add(ValueDataEntry(key, value))
                        }
                        pie.data(data)
                    } else {
                        binding.emptyState.setVisibility(View.VISIBLE)
                        binding.anyChart.setVisibility(View.GONE)
                    }
                }

            })
        viewModel!!.getTransactions(calendar, SELECTED_STATS_TYPE)


        binding.anyChart.setChart(pie)
        return binding.root
    }

    fun updateDate() {
        if (Constants.SELECTED_TAB_STATS == Constants.DAILY) {
            binding.currentDate.setText(formatDate(calendar!!.time))
        } else if (Constants.SELECTED_TAB_STATS == Constants.MONTHLY) {
            binding.currentDate.setText(formatDateByMonth(calendar!!.time))
        }
        viewModel!!.getTransactions(calendar!!, SELECTED_STATS_TYPE)
    }
}