package com.example.androidnc.ui.addstt

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidnc.R
import com.example.androidnc.databinding.FragmentAddSttBinding
import com.example.androidnc.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_add_stt.*


class AddSttFragment : BaseFragment<AddSttViewModel, FragmentAddSttBinding>() {
    var uriGetFromMedia: Uri?=null
    override fun createViewModel(): Class<AddSttViewModel> {
        return AddSttViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.fragment_add_stt
    }

    override fun initView() {
    }

    override fun bindViewModel() {
        btn_addImage.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activity.startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            var uriImg=data.data
            iv_choseImage.setImageURI(uriImg)
            this.uriGetFromMedia=uriImg
        }
    }
}