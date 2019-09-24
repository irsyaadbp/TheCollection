package com.irsyaad.dicodingsubmission.thecollection.ui.activity.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.irsyaad.dicodingsubmission.thecollection.R
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        toolbar.title = getString(R.string.notification)
        setSupportActionBar(toolbar)
    }
}
