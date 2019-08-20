package com.irsyaad.dicodingsubmission.thecollection.ui.activity

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.irsyaad.dicodingsubmission.thecollection.R
import com.irsyaad.dicodingsubmission.thecollection.adapter.ViewPagerAdapter
import com.irsyaad.dicodingsubmission.thecollection.ui.activity.setting.SettingActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var languagePref: SharedPreferences
    private var currentLanguage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        setViewPager()

        languagePref = getSharedPreferences("CURRENT_LANGUAGE", 0)
        currentLanguage = languagePref.getString("language", Locale.getDefault().displayLanguage)

        changeLocale(currentLanguage!!)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val menu = Intent(this,  SettingActivity::class.java)
        startActivity(menu)
        finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        currentLanguage = languagePref.getString("language", Locale.getDefault().displayLanguage)

        if (currentLanguage == null) {
            setViewPager()
            return
        }

        changeLocale(currentLanguage!!)
    }

    private fun setViewPager(){
        viewPager.adapter = ViewPagerAdapter(this, supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

    @Suppress("DEPRECATION")
    private fun changeLocale(lang: String) {
        if (lang.equals("", ignoreCase = true))
            return
        val myLocale = Locale(lang)
        Locale.setDefault(myLocale)
        val config = Configuration()
        config.locale = myLocale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }
}
