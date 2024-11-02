package com.example.easykhatabahi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener

class IntroActivity : AppCompatActivity() {


    private var screenPager: ViewPager? = null
    var introViewPagerAdapter: IntroViewPagerAdapter? = null
    var tabIndicator: TabLayout? = null
    var btnNext: Button? = null
    var position = 0
    var btnGetStarted: Button? = null
    var btnAnim: Animation? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_intro)



        btnNext = findViewById(R.id.btn_next)
        btnGetStarted = findViewById(R.id.btn_get_started)
        tabIndicator = findViewById(R.id.tab_indicator)
        btnAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.button_animation)
        if (restorePrefData()) {
            val mainActivity = Intent(
                applicationContext,
                MainActivity::class.java
            )
            startActivity(mainActivity)
            finish()
        }



        val mList: MutableList<ScreenItem> = ArrayList()
        val fresh_food = mList.add(
            ScreenItem(
                "Track Your Expense",
                "All your Income and "+ "Expense manage at one place",
                R.drawable.track_expense
            )
        )
        mList.add(
            ScreenItem(
                "Set Your Budget",
                "You can set your daily and " +
                        "monthly budget with this app",
                R.drawable.budget
            )
        )
        mList.add(ScreenItem("Welcome You", "Take control of your finances with Expense Manager.", R.drawable.illu))



        screenPager = findViewById(R.id.screen_viewpager)
        introViewPagerAdapter = IntroViewPagerAdapter(this, mList)
        screenPager!!.setAdapter(introViewPagerAdapter)


        tabIndicator!!.setupWithViewPager(screenPager)


        btnGetStarted!!.setOnClickListener(View.OnClickListener {
            val mainActivity = Intent(
                applicationContext,
                MainActivity::class.java
            )
            startActivity(mainActivity)
            savePrefsData()
            finish()
        })



        btnNext!!.setOnClickListener(View.OnClickListener {
            position = screenPager!!.getCurrentItem()
            if (position < mList.size) {
                position++
                screenPager!!.setCurrentItem(position)
            }
            if (position == mList.size - 1) {

                loaddLastScreen()
            }
        })



        tabIndicator!!.addOnTabSelectedListener(object : BaseOnTabSelectedListener<TabLayout.Tab?> {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.position == mList.size - 1) {
                    loaddLastScreen()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })





    }

    private fun savePrefsData() {
        val pref = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("isIntroOpnend", true)
        editor.commit()
    }


    private fun restorePrefData(): Boolean {
        val pref =
            applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        return pref.getBoolean("isIntroOpnend", false)
    }

    private fun loaddLastScreen() {
        btnNext!!.visibility = View.INVISIBLE
        btnGetStarted!!.visibility = View.VISIBLE
        tabIndicator!!.visibility = View.INVISIBLE
        btnGetStarted!!.animation = btnAnim
    }

}