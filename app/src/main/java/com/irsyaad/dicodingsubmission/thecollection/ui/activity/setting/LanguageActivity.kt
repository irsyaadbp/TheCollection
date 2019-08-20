package com.irsyaad.dicodingsubmission.thecollection.ui.activity.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.irsyaad.dicodingsubmission.thecollection.R
import com.irsyaad.dicodingsubmission.thecollection.ui.activity.MainActivity
import kotlinx.android.synthetic.main.activity_language.*

class LanguageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)

        toolbar.title = getString(R.string.language)
        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_back_black_24dp)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val back = Intent(this, SettingActivity::class.java)
        startActivity(back)
        finish()
    }
}
