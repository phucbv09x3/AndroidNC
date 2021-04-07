package com.example.androidnc.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.example.androidnc.BR
import com.example.androidnc.App
import com.example.androidnc.R
import com.example.androidnc.data.DataManager
import com.example.androidnc.ui.addstt.AddSttFragment
import com.example.bitcoin.data.local.service.CoinService
import com.example.bitcoin.data.scheduler.ISchedulerProvider
import com.example.androidnc.utils.printLog
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_add_stt.*
import javax.inject.Inject

/**
 * Created by Phuc on 15/1/2021
 */

abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : AppCompatActivity(),
    Navigators {

    @Inject
    lateinit  var factory: ViewModelFactory

    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var schedulerProvider: ISchedulerProvider

    @Inject
    lateinit var service: CoinService

    private val mDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }
    private val mProgressDialog: BaseFragment.ProgressDialog by lazy {
        BaseFragment.ProgressDialog(this)
    }


    fun inject(
        factory: ViewModelFactory,
        dataManager: DataManager,
        schedulerProvider: ISchedulerProvider,
        service: CoinService
    ) {
        this.factory = factory
        this.dataManager = dataManager
        this.schedulerProvider = schedulerProvider
        this.service=service
    }


    abstract fun createViewModel(): Class<VM>

    abstract fun getContentView(): Int

    abstract fun initAction()

    abstract fun initData()

    lateinit var mViewModel: VM
    lateinit var mDataBinding: DB


    override fun onCreate(savedInstanceState: Bundle?) {
        (application as? App)?.requestInject(this)
        super.onCreate(savedInstanceState)
        mDataBinding = DataBindingUtil.setContentView(this, getContentView())
        mViewModel = ViewModelProviders.of(this, factory).get(createViewModel())
        mDataBinding.setVariable(BR.viewModel, mViewModel)
        trackingProgress()
        initAction()
        initData()

    }




    private fun trackingProgress() {
//        mViewModel.progressBar
//            .subscribeOn(schedulerProvider.io)
//            .observeOn(schedulerProvider.ui)
//            .subscribe {
//                if (it) {
//                    showLoading()
//                } else {
//                    hideLoading()
//                }
//            }.addDisposable()
    }

    override fun showActivity(activity: Class<*>, bundle: Bundle?) {
        val intent = Intent(this, activity)
        intent.putExtras(bundle ?: Bundle())
        startActivity(intent)
    }


    override fun fragmentRequestInject(fragment: BaseFragment<*, *>) {
        factory?.let {
            fragment.inject(it, dataManager, schedulerProvider,service)
        }

    }

    private fun showLoading() {
        if (!mProgressDialog.isShowing && !isFinishing) {
            printLog("showLoading")
            mProgressDialog.show()
        }
    }

    private fun hideLoading() {
        if (mProgressDialog.isShowing && !isFinishing) {
            printLog("hideProgress")
            mProgressDialog.dismiss()
        }
    }

    fun Disposable.addDisposable() {
        mDisposable.add(this)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        AddSttFragment().resultImage(requestCode,resultCode,data)

    }



}
