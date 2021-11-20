package com.ums.tesagri

import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_profile, container, false)
        view!!.prof_name.text=get_data!!.name
        view.prof_email.text=get_data!!.email
        view.prof_acres.text=get_data!!.totalacres
        view.prof_area.text=get_data!!.area
        view.prof_land.text=get_data!!.landno
        view.prof_unique.text=get_data!!.uid
        view.prof_timing.text= get_data!!.timings
        return view
    }
}