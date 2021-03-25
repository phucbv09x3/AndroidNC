package com.example.androidnc.ui.status

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.androidnc.ui.base.BaseViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class StatusViewModel : BaseViewModel() {
    var listStatus=MutableLiveData<MutableList<StatusModel>>()
    private var list= mutableListOf<StatusModel>()
    fun getListStatus(){
        val dataReference=FirebaseDatabase.getInstance().getReference("Status")
        val query=dataReference.limitToFirst(50)
        query.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (pos in snapshot.children){
                    var status=pos.getValue(StatusModel::class.java)
                    var objStatus=StatusModel(
                        pos.child("imageMy").value.toString(),
                        pos.child("img").value.toString(),
                        pos.child("nameMy").value.toString(),
                        pos.child("text").value.toString(),
                        pos.child("uid").value.toString(),
                        pos.child("time").value.toString(),
                        pos.child("demTym").value.toString().toInt()
                    )
                    list.add(objStatus)
                }
                listStatus.value=list
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}