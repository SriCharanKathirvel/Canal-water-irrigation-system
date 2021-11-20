package com.ums.tesagri

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.ums.tesagri.EnableFragment.Companion.get_data
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_enable.view.*
import kotlinx.android.synthetic.main.fragment_request.view.*
import kotlinx.android.synthetic.main.req_view.view.*

class RequestFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_request, container, false)
         retrieveData(view)
        return view
    }

    private fun retrieveData(view: View?) {
       val ref=FirebaseDatabase.getInstance().getReference("/Request/${get_data!!.area}")
        ref.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var adap= GroupAdapter<ViewHolder>()
                 for(h in snapshot.children)
                 {
                     var get_datas=h.getValue(RequestClass::class.java)
                     if(get_datas!=null && get_datas.uid!=FirebaseAuth.getInstance().uid)
                     {
                         adap.add(AdapClass(get_datas))
                     }
                 }
                view!!.req_recycle_view.adapter=adap
            }

        })
    }
    class AdapClass(var data:RequestClass):Item<ViewHolder>()
    {
        override fun getLayout(): Int {
           return R.layout.req_view
        }

        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.req_name_val.text=data.name
            viewHolder.itemView.req_acres_val.text=data.totalacres
            viewHolder.itemView.req_time_val.text=data.timings
            viewHolder.itemView.req_btn.setOnClickListener {
                val ref=FirebaseDatabase.getInstance().getReference("/RequestReceive/${data.uid}").push()
                ref.setValue(RequestReceive(get_data!!.name, get_data!!.timings,get_data!!.totalacres,
                    get_data!!.uid))
                }
            }
        }

    }