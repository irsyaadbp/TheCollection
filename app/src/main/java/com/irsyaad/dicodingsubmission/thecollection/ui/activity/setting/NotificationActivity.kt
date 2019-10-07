package com.irsyaad.dicodingsubmission.thecollection.ui.activity.setting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.irsyaad.dicodingsubmission.thecollection.R
import com.irsyaad.dicodingsubmission.thecollection.reminder.AlarmReceiver
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : AppCompatActivity() {

    private val alarmReceiver: AlarmReceiver = AlarmReceiver()

    private lateinit var notificationPref: SharedPreferences
    private var dailyReminder: Boolean = false
    private var releaseNotification: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        toolbar.title = getString(R.string.notification)
        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_back_black_24dp)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        notificationPref = getSharedPreferences("NOTIFICATION", 0)
        dailyReminder = notificationPref.getBoolean("daily", false)
        releaseNotification = notificationPref.getBoolean("release", false)

        switchDaily.isChecked = dailyReminder
        switchRelease.isChecked = releaseNotification

        setSwitchChangeListener()

        onClick()
    }

    private fun onClick(){
        tvDaily.setOnClickListener {
            switchDaily.isChecked = !switchDaily.isChecked
        }

        tvRelease.setOnClickListener {
            switchRelease.isChecked = !switchRelease.isChecked
        }
    }

    private fun setSwitchChangeListener(){
        val editor = notificationPref.edit()
        switchDaily.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                editor.putBoolean("daily", true)
                editor.apply()

                alarmReceiver.setDailyReminder(this)
            }else{
                editor.putBoolean("daily", false)
                editor.apply()

                alarmReceiver.cancelNotification(this, AlarmReceiver.dailyReminders, getString(R.string.cancel_daily_reminder) )
            }
        }

        switchRelease.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                editor.putBoolean("release", true)
                editor.apply()

                alarmReceiver.setReleaseNotification(this)
            }else{
                editor.putBoolean("release", false)
                editor.apply()

                alarmReceiver.cancelNotification(this, AlarmReceiver.releaseNotification, getString(R.string.cancel_release) )
            }
        }
    }

}
