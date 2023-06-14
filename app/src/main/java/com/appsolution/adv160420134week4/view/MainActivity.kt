package com.appsolution.adv160420134week4.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.appsolution.adv160420134week4.R
import com.appsolution.adv160420134week4.util.createNotificationChannel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    init {
        instance=this
    }

    companion object{
        private var instance:MainActivity?=null

        fun showNotification(title:String, content:String, icon:Int){
            val channelId="${instance?.packageName}-${instance?.getString(R.string.app_name)}"
            val notificationBuilder = NotificationCompat.Builder(instance!!.applicationContext, channelId).apply {
                setSmallIcon(icon)
                setContentTitle(title)
                setContentText(content)
                setStyle(NotificationCompat.BigTextStyle())
                priority = NotificationCompat.PRIORITY_DEFAULT
                setAutoCancel(true)
            }
            val notificationManager = NotificationManagerCompat.from(instance!!.applicationContext.applicationContext!!)
            if (ActivityCompat.checkSelfPermission(
                    instance!!.applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notificationManager.notify(1001, notificationBuilder.build())

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel(this,
            NotificationManagerCompat.IMPORTANCE_DEFAULT, false,
            getString(R.string.app_name), "App notification channel.")

        val fab = findViewById<FloatingActionButton>(R.id.fabNotif)
        fab.setOnClickListener{
            val observable = Observable.timer(5,TimeUnit.SECONDS).apply {
                subscribeOn(Schedulers.io())
                observeOn(AndroidSchedulers.mainThread())
                subscribe{
                    Log.wtf("Observe Message","five Second")
                    showNotification("Dummy","A new notification created",R.drawable.baseline_mail_24)
                }
            }
        }

        val observable = Observable.just("Hello","Welcome to","Ubaya")
        val observer=object: Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.d("observerMessage","Begin Subscribe")
            }

            override fun onError(e: Throwable) {
                Log.e("observerMessage","Error: ${e.message.toString()}")
            }

            override fun onComplete() {
                Log.d("observerMessage","Complete")
            }

            override fun onNext(t: String) {
                Log.d("observerMessage","Data: $t")
            }

        }

        observable.apply {
            subscribeOn(Schedulers.io())
            observeOn(AndroidSchedulers.mainThread())
            subscribe(observer)
        }


    }
}