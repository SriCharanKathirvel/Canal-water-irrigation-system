package com.ums.tesagri

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Homeopen(EnableFragment(),"MOTOR ACTIVATION")
        home_bottom_view.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.checkings->{
                    Homeopen(EnableFragment(),"MOTOR ACTIVATION")
                }
                R.id.request->{
                    Homeopen(RequestFragment(),"REQUEST FOR RESOURCE")
                }
                R.id.profile->{
                    Homeopen(ProfileFragment(),"PROFILE")
                }
               R.id.notifications->{
                   Homeopen(NotificationsFragment(),"NOTIFICATIONS")
               }
            }
            return@setOnNavigationItemSelectedListener(true)
        }
    }

    private fun Homeopen(frag:Fragment,titles:String) {
        supportActionBar!!.title=titles
        supportFragmentManager.beginTransaction().replace(R.id.home_frame,frag).commit()
    }

    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().uid==null)
        {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}