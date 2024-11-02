package com.example.easykhatabahi

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.easykhatabahi.databinding.ActivityMainBinding
import com.example.easykhatabahi.utils.Constants
import com.example.easykhatabahi.views.fragments.info
import com.example.easykhatabahi.viewmodels.MainViewModel
import com.example.easykhatabahi.views.fragments.StatsFragment
import com.example.easykhatabahi.views.fragments.TransactionsFragment

import com.google.android.material.navigation.NavigationBarView
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var calendar: Calendar

    /*
    0 = Daily
    1 = Monthly
    2 = Calendar
    3 = Summary
    4 = Notes
     */
    var viewModel: MainViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setSupportActionBar(binding.toolBar)

        supportActionBar!!.title = "Transactions"
        Constants.setCategories()
        calendar = Calendar.getInstance()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, TransactionsFragment())
        transaction.commit()
        binding.bottomNavigationView.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            val transaction = supportFragmentManager.beginTransaction()
            if (item.itemId == R.id.transactions) {
                setSupportActionBar(binding.toolBar)

                supportActionBar!!.title = "Transactions"
                transaction.replace(R.id.content, TransactionsFragment())
                transaction.addToBackStack(null)
            } else if (item.itemId == R.id.stats) {
                setSupportActionBar(binding.toolBar)
                supportActionBar!!.title="Statistics"
                transaction.replace(R.id.content, StatsFragment())

                transaction.addToBackStack(null)
            }else if (item.itemId == R.id.More) {
                setSupportActionBar(binding.toolBar)
                supportActionBar!!.title="Settings"
                transaction.replace(R.id.content, info())

                transaction.addToBackStack(null)
            }

            transaction.commit()
            true
        })
    }

    val transactions: Unit
        get() {
            viewModel!!.getTransactions(calendar)
        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}