package com.ums.tesagri

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class AgriSplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agri_splash)
        supportActionBar!!.hide()
        Handler().postDelayed({
            val ind= Intent(this,MainActivity::class.java)
            startActivity(ind)
        },2000)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}