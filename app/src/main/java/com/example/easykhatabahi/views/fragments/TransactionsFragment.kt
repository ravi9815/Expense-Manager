package com.example.easykhatabahi.views.fragments



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easykhatabahi.adapters.TransactionsAdapter
import com.example.easykhatabahi.databinding.FragmentTransactionsBinding
import com.example.easykhatabahi.models.Transaction
import com.example.easykhatabahi.utils.Constants
import com.example.easykhatabahi.utils.Helper.formatDate
import com.example.easykhatabahi.utils.Helper.formatDateByMonth
import com.example.easykhatabahi.viewmodels.MainViewModel
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import io.realm.RealmResults
import java.util.Calendar


class TransactionsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var binding: FragmentTransactionsBinding
    lateinit var calendar: Calendar

    /*
    0 = Daily
    1 = Monthly
    2 = Calendar
    3 = Summary
    4 = Notes
     */
    var viewModel: MainViewModel? = null
    private var mRewardedAd: RewardedAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        MobileAds.initialize(requireContext()) {}
        val adRequest = AdRequest.Builder().build()

        binding = FragmentTransactionsBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        calendar = Calendar.getInstance()
        updateDate()






        binding.nextDateBtn.setOnClickListener { c ->
            if (Constants.SELECTED_TAB == Constants.DAILY) {
                calendar.add(Calendar.DATE, 1)
            } else if (Constants.SELECTED_TAB == Constants.MONTHLY) {
                calendar.add(Calendar.MONTH, 1)
            }
            updateDate()
        }
        binding.previousDateBtn.setOnClickListener { c ->
            if (Constants.SELECTED_TAB == Constants.DAILY) {
                calendar.add(Calendar.DATE, -1)
            } else if (Constants.SELECTED_TAB == Constants.MONTHLY) {
                calendar.add(Calendar.MONTH, -1)
            }
            updateDate()
        }
        binding.floatingActionButton.setOnClickListener { c ->

//            ca-app-pub-1940998103342538/5317083542

            RewardedAd.load(requireContext(), "ca-app-pub-1940998103342538/7994193676", adRequest, object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Handle the ad failure
                    AddTransactionFragment().show(
                        parentFragmentManager,
                        null
                    )

                }

                override fun onAdLoaded(rewardedAdd: RewardedAd) {
                    this@TransactionsFragment.mRewardedAd=rewardedAdd



                    showRewardedAd()
                }

            })








            }


        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.text == "Monthly") {
                    Constants.SELECTED_TAB = 1
                    updateDate()
                } else if (tab.text == "Daily") {
                    Constants.SELECTED_TAB = 0
                    updateDate()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        binding.transactionsList.setLayoutManager(LinearLayoutManager(context))
        viewModel!!.transactions.observe(
            viewLifecycleOwner,
            object : Observer<RealmResults<Transaction>?> {
                override fun onChanged(transactions: RealmResults<Transaction>?) {

                    val transactionsAdapter = TransactionsAdapter(activity!!, transactions!!)
                    binding.transactionsList.setAdapter(transactionsAdapter)


                    if (transactions.size > 0) {
                        binding.emptyState.setVisibility(View.GONE)
                    } else {
                        binding.emptyState.setVisibility(View.VISIBLE)
                    }
                }

            })





        viewModel!!.totalIncome.observe(viewLifecycleOwner, object : Observer<Double?> {
            override fun onChanged(aDouble: Double?) {
                binding.incomeLbl.setText(aDouble.toString())
            }

        })
        viewModel!!.totalExpense.observe(viewLifecycleOwner, object : Observer<Double?> {
            override fun onChanged(aDouble: Double?) {
                binding.expenseLbl.setText(aDouble.toString())
            }

        })
        viewModel!!.totalAmount.observe(viewLifecycleOwner, object : Observer<Double?> {
            override fun onChanged(aDouble: Double?) {
                binding.totalLbl.setText(aDouble.toString())
            }

        })
        viewModel!!.getTransactions(calendar)
        return binding.getRoot()
    }

    private fun showRewardedAd() {
        if (mRewardedAd != null) {
            mRewardedAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent()
                {

                    AddTransactionFragment().show(
                parentFragmentManager,
                null
            )


                }
                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    // Ad failed to show.
                }

                override fun onAdShowedFullScreenContent() {
                    // Ad showed.
                }
            }

            mRewardedAd!!.show(requireActivity()) { rewardItem ->
                // Handle the reward (e.g., grant in-app currency)
                val rewardAmount = rewardItem.amount
                val rewardType = rewardItem.type
                // ...
            }
        } else {
            // Ad wasn't loaded yet.
        }
    }


    fun updateDate() {
        if (Constants.SELECTED_TAB == Constants.DAILY) {
            binding.currentDate.setText(formatDate(calendar!!.time))
        } else if (Constants.SELECTED_TAB == Constants.MONTHLY) {
            binding.currentDate.setText(formatDateByMonth(calendar!!.time))
        }
        viewModel!!.getTransactions(calendar)
    }


}