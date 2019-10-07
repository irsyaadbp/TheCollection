package com.irsyaad.dicodingsubmission.thecollection.reminder

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.irsyaad.dicodingsubmission.thecollection.R
import com.irsyaad.dicodingsubmission.thecollection.ui.activity.MainActivity
import java.util.*
import android.app.*
import android.app.PendingIntent

import com.irsyaad.dicodingsubmission.thecollection.BuildConfig.API_KEY

import com.irsyaad.dicodingsubmission.thecollection.model.ListFilmModel
import com.irsyaad.dicodingsubmission.thecollection.model.service.network.ApiRepository
import com.irsyaad.dicodingsubmission.thecollection.ui.activity.detail.DetailFilmActivity
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat


class AlarmReceiver: BroadcastReceiver() {
    private lateinit var time: String

    private val service = ApiRepository.getData()

    private val idDaily = 700
    private var idRelease = 800

    private var requestCode = 200

    companion object{
        const val dailyReminders = "DailyReminder"
        const val releaseNotification = "ReleaseNotification"
    }

    @SuppressLint("SimpleDateFormat")
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == dailyReminders) {
            requestCode = 200
            sendNotification(context, context.getString(R.string.title_daily_reminder), context.getString(
                R.string.msg_daily_reminder), idDaily)
        }

        if(intent.action == releaseNotification){
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val currentDate = sdf.format(Date())

            service.getReleaseFilmToday(API_KEY, currentDate, currentDate)
                .enqueue(object : retrofit2.Callback<ListFilmModel> {
                    override fun onResponse(call: Call<ListFilmModel>, response: Response<ListFilmModel>) {
                        val data = response.body()!!.results

                        if(data.isNotEmpty()) {
                            for (item in data) {
                                sendNotification(
                                    context, "${item.title} ${context.getString(R.string.release)}",
                                    context.getString(R.string.msg_release), idRelease++, item.id
                                )
                                requestCode = idRelease
                            }
                        }

                    }
                    override fun onFailure(call: Call<ListFilmModel>, t: Throwable) {
                        Toast.makeText(context, context.getString(R.string.error_connection), Toast.LENGTH_SHORT).show()
                    }
                })
        }

    }

    fun setDailyReminder(context: Context){
        time = "06:59"

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.action = dailyReminders

        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, idDaily, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        Toast.makeText(context, context.getString(R.string.reminder_setup), Toast.LENGTH_SHORT).show()
    }

    fun setReleaseNotification(context: Context){
        time = "07:58"

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)

        intent.action = releaseNotification

        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, idDaily, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        Toast.makeText(context, context.getString(R.string.release_setup), Toast.LENGTH_SHORT).show()
    }

    private fun sendNotification(context: Context, title: String, message: String?, notifId: Int, idFilm: Int = 0){
        val channelId = "channel_1"
        val channelName = "Notification channel"
        lateinit var intent:Intent

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        if(notifId == idDaily) {
            intent = Intent(context, MainActivity::class.java)
        } else  {
            intent = Intent(context, DetailFilmActivity::class.java)
            intent.putExtra("idFilm", idFilm)
        }


        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_favorite_black_24dp)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setContentIntent(pendingIntent)
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT)

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(channelId)

            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()

        notificationManagerCompat.notify(notifId, notification)
    }

    fun cancelNotification(context: Context, type: String, msg: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val requestCode = if (type == dailyReminders) idDaily else idRelease

        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

}