package com.example.easykhatabahi

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.easykhatabahi.databinding.ActivitySettingsBinding

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         lateinit var binding: ActivitySettingsBinding
        setContentView(R.layout.activity_settings)

        binding.btntheme.setOnClickListener{

            checkToTheme()

            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.choose_theme_text))
            val themes = arrayOf(
                LIGHT,
                DARK,
                DEFAULT
            )
            val checkedItem = MyPreferences(this).darkMode

            builder.setSingleChoiceItems(themes, checkedItem) { dialog, which ->
                when (which) {
                    Numbers.ZERO -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        MyPreferences(this).darkMode = Numbers.ZERO
                        AppCompatDelegate.MODE_NIGHT_NO
                        dialog.dismiss()
                    }
                    Numbers.ONE -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        MyPreferences(this).darkMode = Numbers.ONE
                        AppCompatDelegate.MODE_NIGHT_YES
                        dialog.dismiss()
                    }
                    Numbers.TWO -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                        MyPreferences(this).darkMode = Numbers.TWO
                        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                        dialog.dismiss()
                    }

                    else -> {}
                }
            }
            builder.create().apply {
                show()
            }
        }



    }
    private fun checkToTheme() {
        when (MyPreferences(this).darkMode) {
            Numbers.ZERO -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                AppCompatDelegate.MODE_NIGHT_NO
            }
            Numbers.ONE -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                AppCompatDelegate.MODE_NIGHT_YES
            }
            Numbers.TWO -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        }
    }

    companion object {
        private const val LIGHT = "Light Theme"
        private const val DARK = "Dark Theme"
        private const val DEFAULT = "System Default Theme"
    }
}