package com.ums.tesagri

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    lateinit var emails:String
    lateinit var passs:String
    lateinit var names:String
    lateinit var area:String
    lateinit var totalacres:String
    lateinit var landno:String
    lateinit var serialno:String
    lateinit var age:String
    lateinit var timespin:Spinner
    lateinit var timings:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val time=resources.getStringArray(R.array.Time)//link the resouecs array and assign to time array
        timespin =findViewById(R.id.time_spin)//connect xml file with main file
        var time_adapter=ArrayAdapter(this,android.R.layout.simple_spinner_item,time)//join the array eith adapter
        timespin.adapter=time_adapter
        sign_ups.setOnClickListener {
            UploadData()
            signup_progress.visibility= View.VISIBLE
        }
        sign_ins.setOnClickListener {
            val ind= Intent(this,LoginActivity::class.java)
            startActivity(ind)
        }
        timespin.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                timings=time[position]
            }

        }
    }

    private fun UploadData() {
        emails=email.text.toString()
        passs=pass.text.toString()
        names=name.text.toString()
        area=areas.text.toString()
        totalacres=lands.text.toString()
        landno=land_nos.text.toString()
        serialno=sl_nos.text.toString()
        age=ages.text.toString()
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(emails,passs).addOnCompleteListener {
            if(it.isSuccessful)
            {
                Log.d("TAGSS",it.result!!.user.uid)
                SaveData(it.result!!.user.uid)
            }
        }
    }

    private fun SaveData(uid: String) {

        var ref=FirebaseDatabase.getInstance().getReference("/Account/$uid")
        var req_ref=FirebaseDatabase.getInstance().getReference("/Request/$area/$uid")
        req_ref.setValue(RequestClass(uid,names,totalacres,timings))
        ref.setValue(signUp_Class(uid,names,emails,passs,area,totalacres, landno, serialno, age,timings)).addOnCompleteListener {
            if(it.isSuccessful)
            {
                signup_progress.visibility= View.INVISIBLE
                Toast.makeText(this,"User Added Successfully",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
            }
            else
            {
                signup_progress.visibility= View.INVISIBLE
                Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}