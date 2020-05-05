package pt.isel.bgg.worker

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import pt.isel.bgg.activities.FAVORITE_NAME
import pt.isel.bgg.activities.FavoriteGamesActivity
import pt.isel.bgg.activities.TAG
import pt.isel.bgg.app.BGGApp
import pt.isel.bgg.app.BGGApp.Companion.bggDb
import pt.isel.bgg.app.CHANNEL_ID
import pt.isel.bgg.db.Game
import pt.isel.bgg.model.favorite.FavoriteGame
import java.lang.Exception
import java.util.concurrent.CompletableFuture

const val NOTIFICATION_ID = 100012

class WorkerFavoriteGames(
    private val context: Context,
    private val workerParams: WorkerParameters
) : Worker(
    context,
    workerParams
) {

    val data by  lazy {
        workerParams.inputData
    }
    val name by lazy {
        data.getString("name")!!
    }

    val url by lazy {
        data.getString("url")!!
    }

    override fun doWork(): Result {
        val cf: CompletableFuture<List<Game>> = CompletableFuture()
        BGGApp.bggWebApi.searchFavoriteGames(url,
            onSucess = {
                cf.complete(it)
            },
            onError = {
                cf.completeExceptionally(it)
            })
        try {
            val numberOfRowsBeforeInsert = bggDb.favoriteGameDao().getRowCount(name)
            val apiGames: List<Game> = cf.join()
            Log.v(TAG,apiGames.size.toString())
            bggDb.favoriteGameDao()
                .insertFavoriteGame(*(apiGames.map { game ->
                    FavoriteGame(
                        name,
                        game.id
                    )
                }).toTypedArray())
            if (numberOfRowsBeforeInsert != bggDb.favoriteGameDao().getRowCount(name)) {
                notifyFavoritesChannel(name)
            }

            return Result.success()
        } catch (ex: Exception) {
            return Result.retry()
        }
    }

    private fun notifyFavoritesChannel(name: String) {

        val intent = Intent(applicationContext, FavoriteGamesActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        intent.putExtra(FAVORITE_NAME, name)
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(applicationContext, 0, intent, 0)
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Favorites")
            .setContentText("Update on favorite \"$name\" from Board Games Api ! ")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        /**
         * 5. Send the notification
         */
        NotificationManagerCompat
            .from(applicationContext)
            .notify(NOTIFICATION_ID, notification)
    }
}