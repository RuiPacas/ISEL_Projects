package pt.isel.bgg.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.room.Room
import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import pt.isel.bgg.R
import pt.isel.bgg.api.BGGWebApi
import pt.isel.bgg.db.BGGDb
import pt.isel.bgg.worker.WorkerFavoriteGames
import java.util.concurrent.TimeUnit

const val CHANNEL_ID = "BGG_CHANNEL_FAVORITES"

class BGGApp : Application() {

    companion object {
        lateinit var bggWebApi: BGGWebApi
        lateinit var bggDb: BGGDb

    }

    override fun onCreate() {
        super.onCreate()
        bggWebApi = BGGWebApi(applicationContext)
        bggDb = Room
            .databaseBuilder(applicationContext, BGGDb::class.java, "BGG-Db")
            .build()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        // 1. Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = getString(R.string.channel_name)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // 2. Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun scheduleBackgroundWork(url : String, name:String): PeriodicWorkRequest {
         val request = PeriodicWorkRequestBuilder<WorkerFavoriteGames>(15, TimeUnit.MINUTES)
             .setInitialDelay(10, TimeUnit.MINUTES)
             .setInputData(Data.Builder()
                 .putString("url",url)
                 .putString("name",name).build())
             .build()
        WorkManager.getInstance(applicationContext).enqueue(request)
        return request
    }
}