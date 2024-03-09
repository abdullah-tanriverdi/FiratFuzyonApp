package com.tanriverdi.firatfuzyon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : AppCompatActivity() {


    // Başlangıç ekranının gösterim süresi tanımlanır.
    private val splashTimeOut: Long = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Belirli bir süre gecikme sonrasında istenilen Activitye geçiş yapacak işlemler tanımlanır.
        Handler().postDelayed({
            val intent = Intent (this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, splashTimeOut)
    }

}