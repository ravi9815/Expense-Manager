package com.example.easykhatabahi.views.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.easykhatabahi.MyPreferences
import com.example.easykhatabahi.Numbers
import com.example.easykhatabahi.R
import com.example.easykhatabahi.databinding.FragmentInfoBinding

class info : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private lateinit var binding: FragmentInfoBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentInfoBinding.inflate(inflater)


        binding.btntheme.setOnClickListener{

        checkToTheme()

                val builder = AlertDialog.Builder(context)
                builder.setTitle(getString(R.string.choose_theme_text))
                val themes = arrayOf(
                    LIGHT,
                    DARK,
                    DEFAULT
                )
                val checkedItem = MyPreferences(requireContext()).darkMode

                builder.setSingleChoiceItems(themes, checkedItem) { dialog, which ->
                    when (which) {
                        Numbers.ZERO -> {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                            MyPreferences(requireContext()).darkMode = Numbers.ZERO
                            AppCompatDelegate.MODE_NIGHT_NO
                            dialog.dismiss()
                        }
                        Numbers.ONE -> {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                            MyPreferences(requireContext()).darkMode = Numbers.ONE
                            AppCompatDelegate.MODE_NIGHT_YES
                            dialog.dismiss()
                        }
                        Numbers.TWO -> {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                            MyPreferences(requireContext()).darkMode = Numbers.TWO
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



        return binding.getRoot()
    }



    private fun checkToTheme() {
        when (MyPreferences(requireContext()).darkMode) {
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