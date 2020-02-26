package com.example.notification_setprogress

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var i = 0
    var nm: NotificationManagerCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt1.setOnClickListener {

            nm = NotificationManagerCompat.from(this)

            var n = NotificationCompat.Builder(this, "channel1")
                .setContentTitle("Download")
                .setContentText("Downloading...")
                .setProgress(100, 0, false)
                .setOngoing(true)
                .setOnlyAlertOnce(true)

            nm!!.notify(1, n.build())

            Thread(
                object : Runnable {
                    override fun run() {
                        SystemClock.sleep(3000)
                        while (i <= 100) {
                            runOnUiThread {
                                n.setProgress(100, i, false)
                                nm!!.notify(1, n.build())
                            }

                            SystemClock.sleep(2000)
                            i += 5
                        }
                        runOnUiThread {
                            n.setProgress(0, 0, false)
                            n.setOngoing(false)
                            n.setContentText("Download Completed")
                            nm!!.notify(1, n.build())
                        }

                    }

                }
            ).start()
        }

    }
}
