package com.ums.tesagri

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ums.tesagri.EnableFragment.Companion.get_data
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_notifications.view.*
import kotlinx.android.synthetic.main.fragment_request.view.*
import kotlinx.android.synthetic.main.req_receive_view.view.*
import kotlinx.android.synthetic.main.req_view.view.*
import kotlinx.android.synthetic.main.req_view.view.req_acres_val
import kotlinx.android.synthetic.main.req_view.view.req_name_val
import kotlinx.android.synthetic.main.req_view.view.req_time_val

class NotificationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_notifications, container, false)
        retrieveData(view)
        return view
    }

    private fun retrieveData(view: View?) {
        val ref= FirebaseDatabase.getInstance().getReference("/RequestReceive/${get_data!!.uid}")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var adap= GroupAdapter<ViewHolder>()
                for(h in snapshot.children)
                {
                    var get_datas=h.getValue(RequestReceive::class.java)
                    if(get_datas!=null)
                    {
                        adap.add(AdapClass(get_datas))
                    }
                }
                view!!.notifi_recycle_view.adapter=adap
            }

        })
    }
    class AdapClass(var data:RequestReceive): Item<ViewHolder>()
    {
        override fun getLayout(): Int {
            return R.layout.req_receive_view
        }

        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.req_name_val.text=data.name
            viewHolder.itemView.req_acres_val.text=data.acres
            viewHolder.itemView.req_time_val.text=data.time
            viewHolder.itemView.accept_btn.setOnClickListener {
                var mytimes= get_data!!.timings.split("-")
                var yourtimes= data.time.split("-").toMutableList()
                var mysthrs:Int?=null
                var mystmin:Int?=null
                var myenhrs:Int?=null
                var myenmin:Int?=null
                var ysthrs:Int?=null
                var ystmin:Int?=null
                var yenhrs:Int?=null
                var yenmin:Int?=null
                var finalhrs:Int?=null
                var finalmin:Int?=null
                var totaltime:String
                if(mytimes.get(0).length==6)
                {
                    mysthrs=mytimes.get(0).slice(listOf(0)).toInt()
                    mystmin=mytimes.get(0).slice(listOf(2,3)).toInt()
                }
                else
                {
                    mysthrs=mytimes.get(0).slice(listOf(0,1)).toInt()
                    mystmin=mytimes.get(0).slice(listOf(3,4)).toInt()
                }

                if(mytimes.get(1).length==6)
                {
                    myenhrs=mytimes.get(1).slice(listOf(0)).toInt()
                    myenmin=mytimes.get(1).slice(listOf(2,3)).toInt()
                }
                else
                {
                    myenhrs=mytimes.get(1).slice(listOf(0,1)).toInt()
                    myenmin=mytimes.get(1).slice(listOf(3,4)).toInt()
                }
                //Your time
                if(yourtimes.get(0).length==6)
                {
                    ysthrs=yourtimes.get(0).slice(listOf(0)).toInt()
                    ystmin=yourtimes.get(0).slice(listOf(2,3)).toInt()
                }
                else
                {
                    ysthrs=yourtimes.get(0).slice(listOf(0,1)).toInt()
                    ystmin=yourtimes.get(0).slice(listOf(3,4)).toInt()
                }

                if(yourtimes.get(1).length==6)
                {
                    yenhrs=yourtimes.get(1).slice(listOf(0)).toInt()
                    yenmin=yourtimes.get(1).slice(listOf(2,3)).toInt()
                }
                else
                {
                    yenhrs=yourtimes.get(1).slice(listOf(0,1)).toInt()
                    yenmin=yourtimes.get(1).slice(listOf(3,4)).toInt()
                }
                if(ysthrs>mysthrs)
                {
                    yourtimes[0]=mytimes[0]
                }
                else
                {
                    yourtimes[1]=mytimes[1]
                }
//                var hduration:Int?=null
//                var mduration:Int?=null
//                if(myenmin<mystmin)
//                {
//                    var q=mystmin/myenmin
//                    myenhrs -= q
//                    var rem=60-(mystmin%myenmin)
//                    mduration=rem
//                }
//                else
//                {
//                    mduration=myenmin-mystmin
//                }
//                hduration=myenhrs-mysthrs
//                if(mysthrs<ysthrs)
//                {
//                    if(mduration<ystmin)
//                    {
//                        finalmin=ystmin-mduration
//                    }
//                    else
//                    {
//                        var q=mduration/ystmin
//                        ysthrs -= q
//                        var rem=60-(mduration%ystmin)
//                        finalmin=rem
//                    }
//                    finalhrs=ysthrs-hduration
//                    if(finalhrs>=12)
//                    {
//                        yourtimes[0]="${finalhrs}:{$finalmin}Pm"
//                        totaltime="${yourtimes[0]}-${yourtimes[1]}"
//                    }
//                    else
//                    {
//                        yourtimes[0]="${finalhrs}:{$finalmin}Am"
//                        totaltime="${yourtimes[0]}-${yourtimes[1]}"
//                    }
//                }
//                else
//                {
//
//                }
                totaltime="${yourtimes[0]}-${yourtimes[1]}"
                val ref= FirebaseDatabase.getInstance().getReference("/Account/${data.uid}")
                ref.addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {

                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val getsdata=snapshot.getValue(signUp_Class::class.java)
                        val ref= FirebaseDatabase.getInstance().getReference("/Account/${data.uid}").setValue(signUp_Class
                            (getsdata!!.uid,getsdata.name,getsdata.email,getsdata.password,getsdata.area,getsdata.totalacres,getsdata.landno,getsdata.serialno,getsdata.age,totaltime))
                    }

                })
            }
        }
    }

}