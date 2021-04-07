package com.example.androidnc.ui.addstt


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.androidnc.R
import com.example.androidnc.databinding.FragmentAddSttBinding
import com.example.androidnc.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_add_stt.*
import java.text.SimpleDateFormat
import java.util.*


class AddSttFragment : BaseFragment<AddSttViewModel, FragmentAddSttBinding>() {
    var uriGetFromMedia: Uri? = null
    lateinit var currentUser: FirebaseUser
    private var getUrlImageMy: String?=null
    private var nameMy: String = ""
    override fun createViewModel(): Class<AddSttViewModel> {
        return AddSttViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.fragment_add_stt
    }

    override fun initView() {
        currentUser = FirebaseAuth.getInstance().currentUser
        val dataReference = FirebaseDatabase.getInstance().getReference("Account").child(
            currentUser.uid
        )
        dataReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("name").value.toString()
                val img = snapshot.child("img").value.toString()
                tv_my.text = name
                this@AddSttFragment.getUrlImageMy = img
                this@AddSttFragment.nameMy = name
                val avatar = view?.findViewById<CircleImageView>(R.id.cv_imageMy)
                if (avatar != null) {
                    Glide.with(avatar).load(img).error(R.drawable.iconapp).into(avatar)
                }

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun bindViewModel() {
        btn_addImage.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activity.startActivityForResult(intent, 111)
        }

        btn_add.setOnClickListener {
//            val pr = ProgressDialog(context)
//            pr.show()
            if (ed_add.text.toString().trim().isEmpty() && uriGetFromMedia == null) {
                Toast.makeText(context, "Bài viết trống !! ", Toast.LENGTH_LONG).show()
            } else {
                Log.d("mot","ok")
              //  pr.show()
                if ( uriGetFromMedia!= null) {
                    ed_add.text.toString().trim().let {
                        val imgRe = FirebaseStorage.getInstance().getReference("Status")
                            .child(currentUser.uid)
                            .child("image")
                        val imgname = imgRe.child("" + uriGetFromMedia)
                        imgname.putFile(uriGetFromMedia!!)
                            .addOnSuccessListener {
                                imgname.downloadUrl.addOnSuccessListener { p0 ->
                                    val imageStore =
                                        FirebaseDatabase.getInstance().getReference("Status")
                                    val hashMap = HashMap<String, Any>()
                                    val key = imageStore.push().key
                                    hashMap["img"] = p0.toString()
                                    hashMap["imageMy"] = getUrlImageMy!!
                                    hashMap["nameMy"] = nameMy
                                    hashMap["text"] = ed_add.text.toString()
                                    hashMap["uid"] = currentUser.uid
                                    hashMap["time"] = getCurrentDate().toString()
                                    hashMap["demTym"] = 0.toString()
                                    hashMap["keySta"] = key.toString()
                                    imageStore.child(currentUser.uid).setValue(hashMap)
                                        .addOnSuccessListener {
                                            //pr.dismiss()
                                            Toast.makeText(
                                                context,
                                                "Upload success..",
                                                Toast.LENGTH_LONG
                                            ).show()

                                            ed_add.setText("")
                                        }.addOnFailureListener {
                                            Toast.makeText(
                                                context,
                                                "no Upload ..",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                }
                            }
                    }


                }

            }
        }


    }

    private fun getCurrentDate(): String {
        val date = Date()
        val dateFormat = "dd/MM/yyyy hh:mm"
        val sdf = SimpleDateFormat(dateFormat)
        return sdf.format(date)
    }


    fun resultImage(requestCode: Int, resultCode: Int, data: Intent?)  {
        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("test", "test")
            val uriImg = data.data

            Log.d("test","${data.data}")
            this.uriGetFromMedia=uriImg
            uriGetFromMedia?.let {
                view?.findViewById<ImageView>(R.id.iv_choseImage)?.setImageURI(it)

            }
            Log.d("test","$uriGetFromMedia")

//            val avatar = view?.findViewById<ImageView>(R.id.iv_choseImage)
//            if (avatar != null) {
//                Glide.with(avatar).load(uriImg).error(R.drawable.iconapp).into(avatar)
//            }

        }
    }
}