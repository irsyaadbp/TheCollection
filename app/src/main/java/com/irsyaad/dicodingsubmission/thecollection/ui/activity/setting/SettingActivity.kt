package com.irsyaad.dicodingsubmission.thecollection.ui.activity.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.irsyaad.dicodingsubmission.thecollection.R
import com.irsyaad.dicodingsubmission.thecollection.ui.activity.MainActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        toolbar.title = getString(R.string.setting)
        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_back_black_24dp)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        settingLanguage.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.settingLanguage -> {
                val lang = Intent(this, LanguageActivity::class.java)
                startActivity(lang)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val back = Intent(this, MainActivity::class.java)
        startActivity(back)
        finish()
    }
}
