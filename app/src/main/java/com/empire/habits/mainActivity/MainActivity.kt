package com.empire.habits.mainActivity

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.empire.habits.R
import com.empire.habits.adapters.ItemListAdapter
import com.empire.habits.entity.Word
import com.empire.habits.ui.items.WordViewModel
import com.empire.habits.utils.Globals.*
import com.empire.habits.utils.MainViewChanger
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MainActivity : AppCompatActivity(), MainViewChanger {

    private val wordViewModel: WordViewModel by viewModels {
        WordViewModel.WordViewModelFactory((application as WordsApplication).repository)
    }
    private lateinit var container: CoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.nav).setupWithNavController(navController)
        container = findViewById(R.id.container)
    }

    override var toggleStatus: Int = TOGGLE_OFF

    override fun insertWord(word: Word) {
        wordViewModel.insert(word)
    }

    override fun observeWords(adapter: ItemListAdapter) {
        wordViewModel.allWords.observe(this) { words ->
            words.let { adapter.submitList(it) }
        }
    }

    override fun getItems(): List<Word>? {
        wordViewModel.allWords.observe(this) { item ->
            wordViewModel.items = item
        }
        return wordViewModel.items

    }

    override fun updateCount(count: Float, word: String) {
        wordViewModel.updateCount(count, word)
    }

    override fun itemSize(view: ViewGroup) {
        wordViewModel.allWords.observe(this) { words ->
            if (words.isEmpty()) view.visibility = ViewGroup.VISIBLE
            else view.visibility = ViewGroup.GONE
        }

    }

    private fun sendNotification(title: String, content: String) {
        val calmImage = BitmapFactory.decodeResource(this.resources, R.drawable.meditation)
        val bigPicStyle = NotificationCompat.BigPictureStyle()
                .bigPicture(calmImage)
                .bigLargeIcon(null)
        val fullScreenIntent = Intent(this, MainActivity::class.java)
        val fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_alarm, "Snooze", pendingIntent)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setFullScreenIntent(fullScreenPendingIntent, true)
                .setVisibility(VISIBILITY_PUBLIC)
                .setStyle(bigPicStyle)
                .setLargeIcon(calmImage)
        //.setAutoCancel(true)
        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    override fun startAlarm(title: String, content: String, hour: Int, minute: Int) {
        if (wordViewModel.currentHour() == hour && wordViewModel.currentMinute() == minute){
            sendNotification(title, content)
            val alarmMgr: AlarmManager? = this.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
            val alarmIntent: PendingIntent = Intent(this, MainActivity::class.java).let {
                intent ->  PendingIntent.getService(this, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_NO_CREATE)
            }
            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
            }

            alarmMgr?.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    alarmIntent)
            //if (pendingIntent != null && alarmManager != null) alarmManager?.cancel(alarmIntent)
        }
    }

    override fun showSnackBar() {
        Snackbar.make(container, "Congratulations Keep Going", Snackbar.LENGTH_SHORT)
                .setAction("Undo") {
                    // TODO: undo count increase
        }.show()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            channel.description = CHANNEL_DESCRIPTION
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onStart() {
        super.onStart()
        createNotificationChannel()
        MobileAds.initialize(this){}
    }

    override fun onResume() {
        super.onResume()
        createNotificationChannel()
    }

    override fun startTimer(timeLeftTv: TextView, progressBar: ProgressBar, startBtn: MaterialButton, stopPhubing: TextView) {
        wordViewModel.countDownTimer(timeLeftTv, progressBar, startBtn, stopPhubing)

    }
}