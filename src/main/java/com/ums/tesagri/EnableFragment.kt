package com.ums.tesagri

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_enable.*
import kotlinx.android.synthetic.main.fragment_enable.view.*
import kotlinx.android.synthetic.main.fragment_enable.view.enable_run_name
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class EnableFragment : Fragment() {
    companion object{
        var get_data:signUp_Class?=null
    }
    var mysthrs:Int?=null
    var mystmin:Int?=null
    var myehrs:Int?=null
    var myemin:Int?=null
    var times:List<String>?=null
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_enable, container, false)
        retrieveData(view)
        view.enable_btn.setOnClickListener {
            val calendar=Calendar.getInstance()
            val time=SimpleDateFormat("kk:mm").format(calendar.time).toString()
            val actualhrs=time.slice(listOf(0,1)).toInt()
            val actualmin=time.slice(listOf(3,4)).toInt()
            if(times!!.get(0).length==6)
            {
                mysthrs=times!!.get(0).slice(listOf(0)).toInt()
                mystmin=times!!.get(0).slice(listOf(2,3)).toInt()
            }
            else
            {
                mysthrs=times!!.get(0).slice(listOf(0,1)).toInt()
                mystmin=times!!.get(0).slice(listOf(3,4)).toInt()
            }

            if(times!!.get(1).length==6)
            {
                myehrs=times!!.get(1).slice(listOf(0)).toInt()
                myemin=times!!.get(1).slice(listOf(2,3)).toInt()
            }
            else
            {
                myehrs=times!!.get(1).slice(listOf(0,1)).toInt()
                myemin=times!!.get(1).slice(listOf(3,4)).toInt()
            }
            if(actualhrs>= mysthrs!! && actualhrs<=myehrs!!) {
                offExistingMotor()
                onMotor()
            }
            else
            {
                Log.d("GETTIME","$actualhrs $mysthrs $myehrs")
                Toast.makeText(view.context,"Dear,${get_data!!.name}It is not your Time",Toast.LENGTH_SHORT).show()
            }
        }
        return view

    }

    private fun offExistingMotor() {
        val ref=FirebaseDatabase.getInstance().getReference("/MotorStatus/${get_data!!.area}")
        ref.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(h in snapshot.children)
                {
                    var Mstatus=h.getValue(MotorStatus::class.java)
                    if(Mstatus!=null && Mstatus.status=="1" && Mstatus.uid!=FirebaseAuth.getInstance().uid)
                    {
                        FirebaseDatabase.getInstance().getReference("MotorStatus/${get_data!!.area}/${Mstatus.uid}").setValue(MotorStatus(Mstatus.name,Mstatus.time,"0",Mstatus.uid))
                        break
                    }
                }
                return
            }

        })
        return
    }

    private fun onMotor() {
        val ref=FirebaseDatabase.getInstance().getReference("/MotorStatus/${get_data!!.area}/${get_data!!.uid}")
        ref.setValue(MotorStatus(get_data!!.name, get_data!!.timings,"1", get_data!!.uid)).addOnCompleteListener {
            if(it.isSuccessful)
            {
                Toast.makeText(view!!.context,"Motor Turn On Successfully",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun retrieveData(view: View) {
        val uid= FirebaseAuth.getInstance().uid
        val ref= FirebaseDatabase.getInstance().getReference("/Account/$uid")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                get_data = snapshot.getValue(signUp_Class::class.java)!!
                view.enable_greet.text="HI,${get_data!!.name}"
                view.enable_your_time.text= get_data!!.timings

                CheckMotor_Status(view, get_data!!.area)
            }
        })
    }
    private fun CheckMotor_Status(view:View,area:String) {
        times= get_data!!.timings.split("-")
//                      myhrs= get_data!!.timings.slice(listOf(0)).toInt()
//                      mymin= get_data!!.timings.slice(listOf(7,8)).toInt()
        val ref=FirebaseDatabase.getInstance().getReference("/MotorStatus/$area")
        ref.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
               for(h in snapshot.children)
               {
                   var Mstatus=h.getValue(MotorStatus::class.java)
                   if(Mstatus!=null && Mstatus.status=="1")
                   {
                       view.enable_run_name.text=Mstatus.name
                       view.enable_run_time.text=Mstatus.time
                   }
               }
            }

        })
    }
}