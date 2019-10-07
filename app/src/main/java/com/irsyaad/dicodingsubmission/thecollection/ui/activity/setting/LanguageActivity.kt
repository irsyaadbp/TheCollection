package com.irsyaad.dicodingsubmission.thecollection.ui.activity.setting

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.irsyaad.dicodingsubmission.thecollection.R
import kotlinx.android.synthetic.main.activity_language.*
import java.util.*

class LanguageActivity : AppCompatActivity() {

    var languagePref: SharedPreferences? = null

    var defaultLanguage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)

        toolbar.title = getString(R.string.language)
        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_back_black_24dp)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        languagePref = getSharedPreferences("CURRENT_LANGUAGE", 0)
        defaultLanguage = Locale.getDefault().displayLanguage
        val language = languagePref!!.getString("language", "" + defaultLanguage)

        if (language == "en" || language == "English" || language == "english") {
            radioEnglish.isChecked = true
        } else {
            radioIndonesian.isChecked = true
        }

        onRadioButtonClicked()
    }

    private fun onRadioButtonClicked() {

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            var lang = ""
            when (checkedId) {
                R.id.radioEnglish -> lang = "en"
                R.id.radioIndonesian -> lang = "in"
            }

            val editor = languagePref!!.edit()
            editor.putString("language", lang)
            editor.apply()

            changeLocale(lang)
        }
    }

    @Suppress("DEPRECATION")
    private fun changeLocale(lang: String) {
        if (lang.equals(""))
            return
        val myLocale = Locale(lang)
        Locale.setDefault(myLocale)
        val config = Configuration()
        config.locale = myLocale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        updateText()
    }

    private fun updateText() {
        toolbar.title = getString(R.string.language)
        radioEnglish.text = getString(R.string.english)
        radioIndonesian.text = getString(R.string.indonesian)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val back = Intent(this, SettingActivity::class.java)
        startActivity(back)
        finish()
    }
}
